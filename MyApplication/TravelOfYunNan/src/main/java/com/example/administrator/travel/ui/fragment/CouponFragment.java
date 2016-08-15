package com.example.administrator.travel.ui.fragment;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.OrdersCouponAdapter;

/**
 * Created by android on 2016/8/14.
 */
public class CouponFragment extends BaseFragment {

    private ListView mLvCoupon;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void initView() {
        mLvCoupon = (ListView) root.findViewById(R.id.lv_coupon);
    }

    @Override
    protected void initData() {
        mLvCoupon.setAdapter(new OrdersCouponAdapter(getContext(),null,true));
    }

    @Override
    protected void initListener() {

    }
}
