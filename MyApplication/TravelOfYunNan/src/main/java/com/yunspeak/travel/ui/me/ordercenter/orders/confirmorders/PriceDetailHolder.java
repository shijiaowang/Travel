package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.bean.BasecPriceBean;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class PriceDetailHolder extends BaseHolder<BasecPriceBean> {
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_price) TextView mTvPrice;
    public PriceDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(BasecPriceBean datas, Context mContext, int position) {
        mTvPrice.setText("¥"+datas.getValue());
        mTvName.setText(datas.getKey());
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_orders_common_price);
    }
}
