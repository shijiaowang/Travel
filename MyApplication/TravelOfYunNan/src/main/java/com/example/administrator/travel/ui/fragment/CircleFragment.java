package com.example.administrator.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.fragment.circlefragment.HotFragment;
import com.example.administrator.travel.ui.fragment.circlefragment.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 圈子Fragment
 */
public class CircleFragment extends BaseFragment {

    private ViewPager mVpCircle;
    private RelativeLayout mRlTitleBg;
    private List<BaseFragment> fragments = new ArrayList<>(2);
    private TextView mTvLeftTitle;
    private TextView mTvRightTitle;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle;
    }


    protected void initView() {
        mRlTitleBg = (RelativeLayout) root.findViewById(R.id.rl_title_bg);
        mVpCircle = (ViewPager) root.findViewById(R.id.vp_circle);
        mTvLeftTitle = (TextView) root.findViewById(R.id.tv_left_title);
        mTvRightTitle = (TextView) root.findViewById(R.id.tv_right_title);
    }

    @Override
    protected void initData() {
        fragments.add(new NavigationFragment());
        fragments.add(new HotFragment());
        mVpCircle.setAdapter(new CirclePagerAdapter(getActivity().getSupportFragmentManager()));
        initTitle(0);
    }

    @Override
    protected void initListener() {
       mVpCircle.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               initTitle(position);
           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    /**
     * 初始化title
     * @param position
     */
    private void initTitle(int position) {
        if (position==0){
            mRlTitleBg.setBackgroundResource(R.drawable.fragment_circle_nav);
            mTvLeftTitle.setTextColor(getResources().getColor(R.color.circleTitleColorChecked));
            mTvRightTitle.setTextColor(getResources().getColor(R.color.circleTitleColorUnChecked));

        }else if (position==1){
            mRlTitleBg.setBackgroundResource(R.drawable.fragment_circle_hot);
            mTvRightTitle.setTextColor(getResources().getColor(R.color.circleTitleColorChecked));
            mTvLeftTitle.setTextColor(getResources().getColor(R.color.circleTitleColorUnChecked));
        }
    }

    class CirclePagerAdapter extends FragmentPagerAdapter {

        public CirclePagerAdapter(FragmentManager fm) {
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
