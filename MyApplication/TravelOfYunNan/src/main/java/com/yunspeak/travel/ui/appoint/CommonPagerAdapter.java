package com.yunspeak.travel.ui.appoint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/12 0012.
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;
    public CommonPagerAdapter(FragmentManager fm, List<Fragment> mDatas) {
        super(fm);
        this.mDatas=mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        if (mDatas==null){
            return 0;
        }
        return mDatas.size();
    }
}
