package com.example.administrator.travel.ui.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.MessagePrivateFragment;
import com.example.administrator.travel.ui.fragment.SystemMessageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 消息中---系统消息
 */
public class SystemMessagePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mDatas=new ArrayList<>(2);
    public SystemMessagePagerAdapter(FragmentManager fm) {
        super(fm);
        mDatas.add(new SystemMessageFragment());
        mDatas.add(new MessagePrivateFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
