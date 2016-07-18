package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.FollowAndFanAdapter;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FollowAndFanFragment extends BaseFragment{

    private ListView mLvFollowFan;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_follow_and_fan;
    }

    @Override
    protected void initView() {
        mLvFollowFan = (ListView) root.findViewById(R.id.lv_follow_fan);

    }

    @Override
    protected void initData() {
        mLvFollowFan.setAdapter(new FollowAndFanAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
