package com.example.administrator.travel.ui.me.ordercenter.orders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersFragment extends LoadAndPullBaseFragment<MyOrdersEvent,MyOrdersBean,MyOrdersBean.DataBean> {

    @BindView(R.id.lv_orders) XListView mLvOrders;


    @Override
    protected void doOtherSuccessData(MyOrdersEvent myOrdersEvent) {
        if (myOrdersEvent.getType()== BaseToolBarActivity.TYPE_DELETE){
            ToastUtils.showToast("订单取消成功");
            mDatas.get(myOrdersEvent.getPosition()).setStatus("2");//订单改为取消
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public XListView setXListView() {
        return mLvOrders;
    }

    @Override
    protected int initResLayout() {
        return R.layout.fragment_orders;
    }

    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return MyOrdersEvent.class;
    }

    @Override
    public void childOnItemClick(AdapterView<?> parent, View view, int position, long id) {

        MyOrdersBean.DataBean dataBean = mDatas.get(position);
        String type = dataBean.getType();
        String orderId = dataBean.getId();
        Intent intent=new Intent(getContext(), OrdersDetailActivity.class);
        intent.putExtra(IVariable.TYPE,type);
        intent.putExtra(IVariable.ID,orderId);
        startActivity(intent);
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_ORDERS_CENTER;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<MyOrdersBean.DataBean> httpData) {
        return new MyOrdersAdapter(getContext(),httpData);
    }
}
