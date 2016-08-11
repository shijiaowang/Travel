package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.MyOrdersAdapter;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class MyOrdersFragment extends BaseFragment {

    private ListView mLvOrders;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_orders;
    }

    @Override
    protected void initView() {
        mLvOrders = (ListView) root.findViewById(R.id.lv_orders);
    }

    @Override
    protected void initData() {
       mLvOrders.setAdapter(new MyOrdersAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
