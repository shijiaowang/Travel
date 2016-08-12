package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.OrdersCouponHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 * 优惠券
 */
public class OrdersCouponAdapter extends TravelBaseAdapter {
    public OrdersCouponAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 3;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new OrdersCouponHolder(mContext);
    }
}
