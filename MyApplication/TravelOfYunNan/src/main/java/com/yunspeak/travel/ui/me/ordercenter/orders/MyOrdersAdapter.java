package com.yunspeak.travel.ui.me.ordercenter.orders;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.MyOrdersBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersAdapter extends BaseRecycleViewAdapter<MyOrdersBean.DataBean> {


    private final int currentType;

    public MyOrdersAdapter(List<MyOrdersBean.DataBean> mDatas, Context mContext, int currentType) {
        super(mDatas, mContext);
        this.currentType = currentType;
    }

    @Override
    public BaseRecycleViewHolder<MyOrdersBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrdersHolder(inflateView(R.layout.item_fragment_orders,parent),currentType);
    }
}
