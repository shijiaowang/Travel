package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.AppointWithMeAdapter;

/**
 * Created by Administrator on 2016/7/21 0021.
 * 带我玩
 */
public class PlayWithMeFragment extends BaseFragment {

    private ListView mPlayWithMe;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint_play_with_me;
    }

    @Override
    protected void initView() {
        mPlayWithMe = (ListView) root.findViewById(R.id.lv_play_with_me);
    }

    @Override
    protected void initData() {
        mPlayWithMe.setAdapter(new AppointWithMeAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
