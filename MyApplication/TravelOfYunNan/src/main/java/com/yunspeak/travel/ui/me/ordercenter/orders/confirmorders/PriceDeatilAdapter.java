package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class PriceDeatilAdapter extends TravelBaseAdapter<BasecPriceBean> {
    public PriceDeatilAdapter(Context mContext, List<BasecPriceBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder,BasecPriceBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new PriceDetailHolder(mContext);
    }
}
