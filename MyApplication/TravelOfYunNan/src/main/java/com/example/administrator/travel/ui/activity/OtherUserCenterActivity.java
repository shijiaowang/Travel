package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.dragtopview.GridViewFragment;
import com.example.administrator.travel.ui.activity.dragtopview.ListViewFragment;
import com.example.administrator.travel.ui.activity.dragtopview.ScrollViewFragment;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconViewPagerIndicator;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.google.common.collect.Lists;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;


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
                    isInflate=true;
                    break;
                case 1:
                    mPbLoad.clearAnimation();
                    mPbLoad.setVisibility(View.GONE);
                    mLlNewUser.setVisibility(View.VISIBLE);
                    isInflate=true;
                    break;
            }
        }
    };
    @ViewInject(R.id.pb_load)
    private View mPbLoad;
    private int i = -1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    private TextView mTvPrivateIcon;
    private TextView mTvFollowIcon;
    @ViewInject(R.id.tv_follow)
    private TextView mTvFollow;
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

    private TextView mTvBack;
    private TextView mTvTitleBack;
    private int mTopViewHeight;
    private int mTitleHeight;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_other_center;
    }

    @Override
    protected void initView() {
        mPbLoad = findViewById(R.id.pb_load);
        inflater = LayoutInflater.from(this);
        mTvPrivateIcon = FontsIconUtil.findIconFontsById(R.id.tv_private_icon, this);
        mTvFollowIcon =  FontsIconUtil.findIconFontsById(R.id.tv_follow_icon, this);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mTvTitleBack = FontsIconUtil.findIconFontsById(R.id.tv_title_back, this);
    }

    @Override
    protected void initListener() {
        mIndicator.setTagClick(mVpDynamic);
        mTvBack.setOnClickListener(this);
        mTvTitleBack.setOnClickListener(this);
        mDragLayout.setOverDrag(false).setCollapseOffset(0).listener(new DragTopLayout.SimplePanelListener() {

            @Override
            public void onSliding(float ratio) {
                if (mTopViewHeight == 0) {
                    mTopViewHeight = mTopView.getHeight();
                }
                if (mTitleHeight==0){
                    mTitleHeight = mVSup.getHeight();
                }
                if (ratio < 1.0 && mRlTitle.getVisibility() != View.GONE) {
                    mRlTitle.setVisibility(View.GONE);
                    mTopView.setVisibility(View.VISIBLE);
                }
                //显示标题
                if (mTopViewHeight * ratio <= mTitleHeight  ) {

                    if (mRlTitle.getVisibility() != View.VISIBLE) {
                        mRlTitle.setVisibility(View.VISIBLE);
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRlTitle.getLayoutParams();
                    layoutParams.height= (int) (mTitleHeight-mTopViewHeight*ratio);
                    mRlTitle.setLayoutParams(layoutParams);
                }
            }
        });
        mVpDynamic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

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

        mIndicator.setTitles(mTitles);

        if (mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        for (int i = 0; i < titles.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitle, false);
            textView.setText(titles[i]);
            mFlTitle.addView(textView);
        }
        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(getFragments(), getTitles());
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);
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
        Fragment listFragment = new ListViewFragment();
        Fragment recyclerFragment = new GridViewFragment();
        Fragment gridViewFragment = new ScrollViewFragment();
        list.add(listFragment);
        list.add(recyclerFragment);
        list.add(gridViewFragment);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (!isInflate) {
            mPbLoad.startAnimation(animationSet);
            mHandler.sendEmptyMessageDelayed(i, 1500);
        }

    }

    // Handle scroll event from fragments
    public void onEvent(Boolean b) {
        mDragLayout.setTouchMode(b);
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
}
