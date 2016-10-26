package com.yunspeak.travel.ui.me.ordercenter.orders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersAdapter extends BaseRecycleViewAdapter<MyOrdersBean.DataBean> {


    public MyOrdersAdapter(List<MyOrdersBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<MyOrdersBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrdersHolder(inflateView(R.layout.item_fragment_orders,parent));
    }
}
