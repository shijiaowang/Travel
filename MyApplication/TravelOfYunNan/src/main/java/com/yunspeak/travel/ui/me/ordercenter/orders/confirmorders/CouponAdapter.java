package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/12 0012.
 * 优惠券
 */
public class CouponAdapter extends TravelBaseAdapter<CouponBean> {
    private boolean isCouponFragment;

    public CouponAdapter(Context mContext, List<CouponBean> mDatas, boolean isCouponFragment) {
        super(mContext, mDatas);
        this.isCouponFragment = isCouponFragment;
    }



    @Override
    protected void initListener(BaseHolder baseHolder, CouponBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CouponHolder(mContext,isCouponFragment);
    }
}
