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
    private boolean isCouponFragment;

    public OrdersCouponAdapter(Context mContext, List mDatas,boolean isCouponFragment) {
        super(mContext, mDatas);
        this.isCouponFragment = isCouponFragment;
    }

    @Override
    protected int testDataSize() {
        return 3;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new OrdersCouponHolder(mContext,isCouponFragment);
    }
}
