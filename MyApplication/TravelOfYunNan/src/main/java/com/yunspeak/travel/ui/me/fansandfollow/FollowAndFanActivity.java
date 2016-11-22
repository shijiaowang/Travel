package com.yunspeak.travel.ui.me.fansandfollow;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.BaseActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/18 0018.
 */

public class FollowAndFanActivity extends BaseToolBarActivity implements View.OnClickListener {
    private int currentPosition = 0;
    private List<Fragment> fragments = new ArrayList<>(2);
    @BindView(R.id.vp_follow_fan) ViewPager mVpFollowFan;//pager
    private TextView mTvFan;
    private TextView mTvFollow;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_follow_and_fan;
    }

    @Override
    protected void initOptions() {
        boolean isFollow = getIntent().getBooleanExtra(MeFragment.FOLLOW_SELECT, false);
        currentPosition = isFollow ? 0 : 1;
        mVpFollowFan.setOnPageChangeListener(new FollowFanChangeListener());
        fragments.add(FanAndFollowFragment.newInstance("1"));
        fragments.add(FanAndFollowFragment.newInstance("2"));
        mVpFollowFan.setAdapter(new FollowFanAdapter(getSupportFragmentManager()));
        mVpFollowFan.setCurrentItem(currentPosition, false);



    }

    @Override
    protected void createTitle() {
        mVsBar.setLayoutResource(R.layout.follow_and_fan_bar);
        mVsBar.inflate();
        mTvFan = (TextView)findViewById(R.id.tv_fan);
        mTvFollow = (TextView)findViewById(R.id.tv_follow);
        mTvFan.setOnClickListener(this);
        mTvFollow.setOnClickListener(this);
        changeSelect();
    }

    @Override
    protected String initTitle() {
        return null;
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
