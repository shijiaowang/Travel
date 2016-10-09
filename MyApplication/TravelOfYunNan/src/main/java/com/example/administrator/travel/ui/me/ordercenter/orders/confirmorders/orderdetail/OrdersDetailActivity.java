package com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders.orderdetail;
import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders.PriceDeatilAdapter;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 订单详情
 */
public class OrdersDetailActivity extends BaseNetWorkActivity<OrdersDetailEvent> {
    @BindView(R.id.tv_order_number) TextView mTvOrderNumber;
    @BindView(R.id.tv_route_price) TextView mTvRoutePrice;
    @BindView(R.id.lv_price) ToShowAllListView mLvPrice;
    @BindView(R.id.tv_reduce_price) TextView mTvReducePrice;
    @BindView(R.id.tv_total_price) TextView mTvTotalPrice;
    @BindView(R.id.tv_zfb) FontsIconTextView mTvZfb;
    @BindView(R.id.tv_pay_des) TextView mTvPayDes;
    @BindView(R.id.rl_zfb) RelativeLayout mRlZfb;
    private String id;

    @Override
    protected void initEvent() {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_orders_detail;
    }

    @Override
    protected Activity initDataAndRegisterEventBus() {
        return this;
    }

    @Override
    protected String initTitle() {
        return "订单详情";
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        id = getIntent().getStringExtra(IVariable.ID);
       builder.addId(id);
    }

    @Override
    protected String initUrl() {
        return IVariable.ORDERS_DETAIL;
    }

    @Override
    protected void onSuccess(OrdersDetailEvent ordersDetailEvent) {
        OrdersDetailBean ordersDetailBean = GsonUtils.getObject(ordersDetailEvent.getResult(), OrdersDetailBean.class);
        OrdersDetailBean.DataBean data = ordersDetailBean.getData();
        mTvOrderNumber.setText("订单号:"+data.getOrder_sn());
        mTvReducePrice.setText(IVariable.RMB+"-"+data.getConpou());
        mTvTotalPrice.setText("总计:"+data.getTotal_price());
        mTvRoutePrice.setText(IVariable.RMB+data.getPrice());
        mLvPrice.setAdapter(new PriceDeatilAdapter(this,data.getBasec_price()));
    }



}
