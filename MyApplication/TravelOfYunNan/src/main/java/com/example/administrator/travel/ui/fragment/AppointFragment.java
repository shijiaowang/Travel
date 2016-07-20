package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.AppointAdapter;

/**
 * Created by Administrator on 2016/7/20 0020.
 * 约伴
 */
public class AppointFragment extends BaseFragment {

    private ListView mLvAppoint;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    protected void initView() {
        mLvAppoint = (ListView) root.findViewById(R.id.lv_appoint);
    }

    @Override
    protected void initData() {
        mLvAppoint.setAdapter(new AppointAdapter(getContext(),null));

    }

    @Override
    protected void initListener() {

    }
}
