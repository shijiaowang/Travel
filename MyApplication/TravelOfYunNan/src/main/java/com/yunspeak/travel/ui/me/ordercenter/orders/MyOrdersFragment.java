package com.yunspeak.travel.ui.me.ordercenter.orders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;
import android.widget.AdapterView;


import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;

import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersFragment extends LoadAndPullBaseFragment<MyOrdersEvent,MyOrdersBean,MyOrdersBean.DataBean> {

   public static final int ALL_ORDERS=1;
    public static final int RECENT_ORDERS=2;
    private int currentType=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentType = getArguments().getInt(IVariable.TYPE);
    }

    @Override
    protected void doOtherSuccessData(MyOrdersEvent myOrdersEvent) {
        if (myOrdersEvent.getType()== BaseToolBarActivity.TYPE_DELETE){
            ToastUtils.showToast("订单取消成功");
            mDatas.get(myOrdersEvent.getPosition()).setStatus("2");//订单改为取消
            mAdapter.notifyItemRemoved(myOrdersEvent.getPosition());
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        changeMargin(4,0);
    }
    public static MyOrdersFragment newInstance(int type) {
        MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IVariable.TYPE, type);
        myOrdersFragment.setArguments(bundle);
        return myOrdersFragment;
    }

    @Override
    public void onSuccess(MyOrdersEvent myOrdersEvent) {
        if (getUserVisibleHint()) {
            super.onSuccess(myOrdersEvent);
        }
    }

    @Override
    protected String initUrl() {
        if (currentType==RECENT_ORDERS){
            return IVariable.RECENT_ORDERS;
        }
        return IVariable.MY_ORDERS_CENTER;
    }

    @Override
    protected BaseRecycleViewAdapter<MyOrdersBean.DataBean> initAdapter(List<MyOrdersBean.DataBean> httpData) {
        return new MyOrdersAdapter(httpData,getContext());
    }
}
