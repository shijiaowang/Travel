package com.example.administrator.travel.ui.me.othercenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.travel.R;

import com.example.administrator.travel.ui.me.othercenter.useralbum.OtherCenterAlbumFragment;
import com.example.administrator.travel.ui.me.othercenter.userinfo.UserInfoFragment;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class OtherUserCenterActivity extends AppCompatActivity implements View.OnClickListener {

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

    private int i = -1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    @BindView(R.id.pb_load) View mPbLoad;
    @BindView(R.id.tv_private_icon) FontsIconTextView mTvPrivateIcon;
    @BindView(R.id.tv_follow_icon) FontsIconTextView mTvFollowIcon;
    @BindView(R.id.tv_follow) TextView mTvFollow;
    @BindView(R.id.ll_private) LinearLayout mLlPrivate;
    @BindView(R.id.ll_follow) LinearLayout mLlFollow;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.vp_dynamic) ViewPager mVpDynamic;
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    @BindView(R.id.ll_new_user) LinearLayout mLlNewUser;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_name) TextView mTvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_other_center);
        inflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        initData();

    }



    protected void initData() {
        initChild();
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager());
        mVpDynamic.setAdapter(adapter);
        mVpDynamic.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mVpDynamic);
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

    private void initChild() {
        if (mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        for (int i = 0; i < titles.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitle, false);
            textView.setText(titles[i]);
            mFlTitle.addView(textView);
        }
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



    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>();
        Fragment listFragment = new OtherCenterAlbumFragment();
        Fragment listFragment2 = new UserInfoFragment();
        Fragment listFragment1 = new OtherCenterAlbumFragment();
       // Fragment gridViewFragment = new RecyclerFragmentDynamic();
        //Fragment scrollViewFragment = new ScrollViewFragment();
        list.add(listFragment);
        list.add(listFragment1);
        list.add(listFragment2);
        //.add(gridViewFragment);
        //list.add(scrollViewFragment);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInflate) {
            mPbLoad.startAnimation(animationSet);
            mHandler.sendEmptyMessageDelayed(i, 1500);
        }

    }



    @Override
    public void onClick(View v) {

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

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
