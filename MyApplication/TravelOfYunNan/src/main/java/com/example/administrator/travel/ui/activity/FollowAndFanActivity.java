package com.example.administrator.travel.ui.activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.FanFragment;
import com.example.administrator.travel.ui.fragment.MeFragment;
import com.example.administrator.travel.ui.fragment.FollowFragment;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝和关注
 */

public class FollowAndFanActivity extends BaseActivity implements View.OnClickListener {
    private int currentPosition = 0;
    private List<BaseFragment> fragments = new ArrayList<>(2);

  @ViewInject(R.id.vp_follow_fan)
    private ViewPager mVpFollowFan;//pager

    private TextView mTvBack;//返回
    @ViewInject(R.id.tv_fan)
    private TextView mTvFan;//粉丝
    @ViewInject(R.id.tv_follow)
    private TextView mTvFollow;//关注

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_follow_and_fan;
    }

    @Override
    protected void initView() {
        boolean isFollow = getIntent().getBooleanExtra(MeFragment.FOLLOW_SELECT, false);
        currentPosition = isFollow ? 0 : 1;
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
    }

    @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mTvFan.setOnClickListener(this);
        mTvFollow.setOnClickListener(this);
        mVpFollowFan.setOnPageChangeListener(new FollowFanChangeListener());
    }

    @Override
    protected void initData() {
        fragments.add(new FollowFragment());
        fragments.add(new FanFragment());

        mVpFollowFan.setAdapter(new FollowFanAdapter(getSupportFragmentManager()));
        mVpFollowFan.setCurrentItem(currentPosition, false);
        changeSelect();
    }

    private void changeSelect() {
        if (currentPosition < 0 || currentPosition > fragments.size()) {
            return;
        }
        if (currentPosition == 1) {
            mTvFan.setTextColor(getResources().getColor(R.color.homeTitleBg));
            mTvFan.setBackgroundResource(R.drawable.activity_follow_fan_right);
            mTvFollow.setBackgroundColor(Color.TRANSPARENT);
            mTvFollow.setTextColor(Color.parseColor("#ffffff"));

        } else {
            mTvFollow.setTextColor(getResources().getColor(R.color.homeTitleBg));
            mTvFollow.setBackgroundResource(R.drawable.activity_follow_fan_left);
            mTvFan.setBackgroundColor(Color.TRANSPARENT);
            mTvFan.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_follow:
                currentPosition=0;
                changeSelect();
                mVpFollowFan.setCurrentItem(currentPosition,false);
                break;
            case R.id.tv_fan:
                currentPosition=1;
                changeSelect();
                mVpFollowFan.setCurrentItem(currentPosition, false);
                break;
        }
    }

    class FollowFanChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            changeSelect();

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class FollowFanAdapter extends FragmentPagerAdapter {

        public FollowFanAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }




}
