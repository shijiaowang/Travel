package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.bean.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/12 0012.
 * 优惠券
 */
public class CouponAdapter extends BaseRecycleViewAdapter<CouponBean> {
    private boolean isCouponFragment;

    public CouponAdapter(Context mContext, List<CouponBean> mDatas, boolean isCouponFragment) {
        super(mDatas,mContext);
        this.isCouponFragment = isCouponFragment;
    }

    @Override
    public BaseRecycleViewHolder<CouponBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponHolder(inflateView(R.layout.item_activity_confirm_orders,parent),isCouponFragment);
    }
}
