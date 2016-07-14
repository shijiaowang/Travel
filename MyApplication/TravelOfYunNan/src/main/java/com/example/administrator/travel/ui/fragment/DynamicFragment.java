package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DynamicAdapter;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class DynamicFragment extends BaseFragment {

    private ListView mLvDiscuss;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        mLvDiscuss = (ListView) root.findViewById(R.id.lv_discuss);

    }

    @Override
    protected void initData() {
         mLvDiscuss.setAdapter(new DynamicAdapter(getActivity(),null));
    }

    @Override
    protected void initListener() {

    }
}
