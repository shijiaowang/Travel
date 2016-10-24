package com.yunspeak.travel.ui.me.titlemanage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 */
public class TitlePagerAdapter extends FragmentPagerAdapter {

    private  List<Fragment> fragments;
    private  String[] mTitles;

    public TitlePagerAdapter(FragmentManager fm, List<Fragment> fragments,String [] mTitles) {
        super(fm);
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}