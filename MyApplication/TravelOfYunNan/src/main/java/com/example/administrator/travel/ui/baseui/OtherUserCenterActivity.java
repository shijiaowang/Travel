package com.example.administrator.travel.ui.baseui;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.event.DragEvent;
import com.example.administrator.travel.ui.baseui.drag.RecyclerBaseFragment;
import com.example.administrator.travel.ui.baseui.drag.RecyclerFragmentDynamic;
import com.example.administrator.travel.ui.baseui.drag.RecyclerFragmentAlbum;
import com.example.administrator.travel.ui.baseui.drag.ScrollViewFragment;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.FontsIconViewPagerIndicator;
import com.example.administrator.travel.utils.LogUtils;
import com.google.common.collect.Lists;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import github.chenupt.dragtoplayout.DragTopLayout;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;


/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class OtherUserCenterActivity extends BaseActivity implements View.OnClickListener {

    private String[] mTitles = new String[]{"动态", "相册", "个人"};
    private String[] titles = {"老司机", "新司机", "旧司机", "486", "我是老司机", "新手", "小清新", "我勒个去去", "速度"};
    private boolean isInflate = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isInflate) {
                return;
            }
            mHandler.removeCallbacksAndMessages(null);//移除所有的参数和消息
            switch (msg.what) {
                case 0:
                    mPbLoad.clearAnimation();
                    mPbLoad.setVisibility(View.GONE);
                    mFlTitle.setVisibility(View.VISIBLE);
                    isInflate = true;
                    break;
                case 1:
                    mPbLoad.clearAnimation();
                    mPbLoad.setVisibility(View.GONE);
                    mLlNewUser.setVisibility(View.VISIBLE);
                    isInflate = true;
                    break;
            }
        }
    };
    @ViewInject(R.id.pb_load)
    private View mPbLoad;
    private int i = -1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    @ViewInject(R.id.tv_private_icon)
    private FontsIconTextView mTvPrivateIcon;
    @ViewInject(R.id.tv_follow_icon)
    private FontsIconTextView mTvFollowIcon;
    @ViewInject(R.id.tv_follow)
    private TextView mTvFollow;
    @ViewInject(R.id.rl_root)
    private RelativeLayout mRlRoot;
    @ViewInject(R.id.ll_private)
    private LinearLayout mLlPrivate;
    @ViewInject(R.id.ll_follow)
    private LinearLayout mLlFollow;

    @ViewInject(R.id.vp_dynamic)
    private ViewPager mVpDynamic;
    @ViewInject(R.id.drag_layout)
    private DragTopLayout mDragLayout;
    private ModelPagerAdapter adapter;
    @ViewInject(R.id.fivpi_indicator)
    private FontsIconViewPagerIndicator mIndicator;
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    @ViewInject(R.id.ll_new_user)
    private LinearLayout mLlNewUser;
    @ViewInject(R.id.top_view)
    private LinearLayout mTopView;
    @ViewInject(R.id.rl_title)
    private RelativeLayout mRlTitle;
    @ViewInject(R.id.v_sup)
    private View mVSup;
    @ViewInject(R.id.tv_back)
    private FontsIconTextView mTvBack;
    @ViewInject(R.id.tv_title_back)
    private FontsIconTextView mTvTitleBack;
    private int mTopViewHeight;
    private int mTitleHeight;
    private String phoneName;

    private int currentIndex = 0;
    private Fragment fragment;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_center;
    }

    @Override
    protected void initView() {
        phoneName = android.os.Build.MODEL;
        mPbLoad = findViewById(R.id.pb_load);
        inflater = LayoutInflater.from(this);

    }

    @Override
    protected void initListener() {
        mIndicator.setTagClick(mVpDynamic);
        mTvBack.setOnClickListener(this);
        mTvTitleBack.setOnClickListener(this);
        mDragLayout.setOverDrag(true).setCollapseOffset(0).setPanelListener(new DragTopLayout.PanelListener() {

            @Override
            public void onPanelStateChanged(DragTopLayout.PanelState panelState) {
               /* // 有一个到顶部，其他都到顶部
                if (panelState == DragTopLayout.PanelState.EXPANDED) {
                    for (int i = 0; i < getFragments().size(); i++) {
                        if (i != currentIndex && i < 2) {//前两个才是
                            ((RecyclerBaseFragment) (getFragments().get(i))).scrollToFirstItem();
                        }
                    }
                }*/
                if (panelState== DragTopLayout.PanelState.COLLAPSED && currentIndex<2){
                    boolean shouldDelegateTouch = ((RecyclerBaseFragment) (getFragments().get(currentIndex))).getShouldDelegateTouch();
                    LogUtils.e(shouldDelegateTouch+"是否需要拦截");
                    mDragLayout.setTouchMode(shouldDelegateTouch);

                }

            }

            @Override
            public void onSliding(float ratio) {
                if (mTopViewHeight == 0) {
                    mTopViewHeight = mTopView.getHeight();
                }
                if (mTitleHeight == 0) {
                    mTitleHeight = mVSup.getHeight();
                }
                if (ratio < 1.0 && mRlTitle.getVisibility() != View.GONE) {
                    mRlTitle.setVisibility(View.GONE);
                    mTopView.setVisibility(View.VISIBLE);
                }
                //显示标题
                if (mTopViewHeight * ratio <= mTitleHeight) {

                    if (mRlTitle.getVisibility() != View.VISIBLE) {
                        mRlTitle.setVisibility(View.VISIBLE);
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRlTitle.getLayoutParams();
                    layoutParams.height = (int) (mTitleHeight - mTopViewHeight * ratio);
                    mRlTitle.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onRefresh() {

            }
        });
        mVpDynamic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setSelectedFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mLlFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlFollow.setBackgroundResource(R.drawable.activity_other_follow);
                mTvFollow.setTextColor(Color.parseColor("#fafafa"));
                mTvFollowIcon.setTextColor(Color.parseColor("#fafafa"));
                mTvFollow.setText("已关注");
                mTvFollowIcon.setText(getResources().getString(R.string.activity_other_followed));
            }
        });
    }


    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mIndicator.setTitles(mTitles);
        if (mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        for (int i = 0; i < titles.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitle, false);
            textView.setText(titles[i]);
            mFlTitle.addView(textView);
        }
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager());
        mVpDynamic.setAdapter(adapter);
        //获取数据
        Random random = new Random();
        i = random.nextInt(2);
        if (i == 0) {
            mFlTitle.setVisibility(View.INVISIBLE);
            mLlNewUser.setVisibility(View.GONE);
        } else {
            mLlNewUser.setVisibility(View.INVISIBLE);

            mFlTitle.setVisibility(View.GONE);
        }
        initAnimation();
    }


    private void initAnimation() {
        animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 0.8f, 0.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        //
        RotateAnimation rotateAnimation = new RotateAnimation(0, 8888, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setFillAfter(true);


        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);


    }

    private List<String> getTitles() {
        return Lists.newArrayList("相册", "动态", "个人");
    }

    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>();
        Fragment listFragment = new RecyclerFragmentAlbum();
        Fragment gridViewFragment = new RecyclerFragmentDynamic();
        Fragment scrollViewFragment = new ScrollViewFragment();
        list.add(listFragment);
        list.add(gridViewFragment);
        list.add(scrollViewFragment);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //使用透明度解决魅族手机闪屏问题
        if (Build.MANUFACTURER.contains("Meizu") || Build.MANUFACTURER.contains("魅族")) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(1500);
            alphaAnimation.setFillAfter(true);
            mRlRoot.startAnimation(alphaAnimation);
        }
        if (!isInflate) {
            mPbLoad.startAnimation(animationSet);
            mHandler.sendEmptyMessageDelayed(i, 1500);
        }

    }

    private void setSelectedFragment(int type) {
        currentIndex = type;
        // update status
        //updateTopStatus(currentIndex);

    }

    /**
     * todo：2.切换时更新各个Viewpager下Fragment的状态
     *
     * @param currentIndex
     */
    private void updateTopStatus(int currentIndex) {
        fragment = getFragments().get(currentIndex);
        if (mDragLayout.getState() != DragTopLayout.PanelState.COLLAPSED) {
            if (isRecycleFragment() && !((RecyclerBaseFragment) fragment).getShouldDelegateTouch()) {
                // topView显示时，却不是首条显示时，更换为首条显示===防止意外情况，实际可能不会出现
                ((RecyclerBaseFragment) fragment).scrollToFirstItem();
                mDragLayout.setTouchMode(true);
            }
        } else if (isRecycleFragment()) {
            mDragLayout.setTouchMode(((RecyclerBaseFragment) fragment).getShouldDelegateTouch());
        }
    }

    public boolean isRecycleFragment() {
        return currentIndex < 2;
    }


    @Subscribe
    public void onEvent(DragEvent dragEvent) {
        if (dragEvent.getIndex()!=currentIndex)return;
        boolean canScroll = dragEvent.isTouch();
        if (currentIndex < 2 && mDragLayout.getState() == DragTopLayout.PanelState.COLLAPSED) {
            RecyclerBaseFragment recyclerBaseFragment = (RecyclerBaseFragment) getFragments().get(currentIndex);
            canScroll = recyclerBaseFragment.getShouldDelegateTouch();
        }
        mDragLayout.setTouchMode(canScroll);
        if (!dragEvent.isTouch() && mDragLayout.getState() == DragTopLayout.PanelState.EXPANDED) {
            // 防止异常情况的补充
            mDragLayout.closeTopView(false);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
            case R.id.tv_title_back:
                finish();
                break;

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return OtherUserCenterActivity.this.getFragments().get(position);
        }

        @Override
        public int getCount() {
            return OtherUserCenterActivity.this.getFragments().size();
        }
    }
}
