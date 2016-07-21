package com.example.administrator.travel.ui.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mDatas;
    public CommonPagerAdapter(FragmentManager fm, List<BaseFragment> mDatas) {
        super(fm);
        this.mDatas=mDatas;
    }

    @Override
    public BaseFragment getItem(int position) {
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
