package com.yunspeak.travel.ui.me.ordercenter.orders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.me.ordercenter.OrdersCenterActivity;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersFragment extends LoadAndPullBaseFragment<MyOrdersEvent,MyOrdersBean,MyOrdersBean.DataBean> {

    private int currentType=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentType = getArguments().getInt(IVariable.TYPE);
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
        if (getUserVisibleHint() && myOrdersEvent.getOrderType()==currentType) {
            if (myOrdersEvent.getType()== IState.TYPE_DELETE){
                ToastUtils.showToast("订单取消成功");
                mDatas.get(myOrdersEvent.getPosition()).setStatus("2");//订单改为取消
                mAdapter.notifyDataSetChanged();
            }else {
                super.onSuccess(myOrdersEvent);
            }
        }
    }

    @Override
    protected String initUrl() {
        if (currentType== OrdersCenterActivity.RECENT_ORDERS){
            return IVariable.RECENT_ORDERS;
        }
        return IVariable.MY_ORDERS_CENTER;
    }

    @Override
    protected void doSuccess(MyOrdersEvent myOrdersEvent) {
        if (myOrdersEvent.getOrderType()==currentType) {//只有等于当前状态才修改
            super.doSuccess(myOrdersEvent);
        }
    }

    @Override
    public MyOrdersEvent getTInstance() {
        MyOrdersEvent myOrdersEvent=new MyOrdersEvent();
        myOrdersEvent.setOrderType(currentType);
        return myOrdersEvent;
    }
    @Subscribe
   public void onEvent(PayNotifyEvent payNotifyEvent){
       onLoad(TYPE_REFRESH);
   }
    @Override
    protected BaseRecycleViewAdapter<MyOrdersBean.DataBean> initAdapter(List<MyOrdersBean.DataBean> httpData) {
        return new MyOrdersAdapter(httpData,getContext(),currentType);
    }
}
