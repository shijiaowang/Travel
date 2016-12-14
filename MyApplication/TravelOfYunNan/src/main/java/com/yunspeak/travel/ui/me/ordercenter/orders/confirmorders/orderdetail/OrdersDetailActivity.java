package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.CostSettingAdapter;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.PayNotifyEvent;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 订单详情
 */
public class OrdersDetailActivity extends BaseNetWorkActivity<OrdersDetailEvent> {
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_route_price)
    TextView mTvRoutePrice;
    @BindView(R.id.lv_price)
    RecyclerView mLvPrice;
    @BindView(R.id.tv_reduce_price)
    TextView mTvReducePrice;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_zfb)
    FontsIconTextView mTvZfb;
    @BindView(R.id.tv_pay_des)
    TextView mTvPayDes;
    @BindView(R.id.rl_zfb)
    RelativeLayout mRlZfb;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_pay_way)
    LinearLayout llPayWay;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_active_name)
    TextView mTvActiveName;
    private String id;
    private int orderType;


    @Override
    protected void initEvent() {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_orders_detail;
    }


    @Override
    protected String initTitle() {
        return "订单详情";
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        id = getIntent().getStringExtra(IVariable.ID);
        orderType = getIntent().getIntExtra(IVariable.TYPE,-1);
        if (orderType==-1) {
            setErrorPage(true);
            return;
        }
        builder.addId(id).add("pay_type",orderType+"");
    }

    @Override
    protected String initUrl() {
        return IVariable.ORDERS_DETAIL;
    }
    @Subscribe
    public void onEvent(PayNotifyEvent payNotifyEvent){
        finish();
    }
    @Override
    protected void onSuccess(OrdersDetailEvent ordersDetailEvent) {
        OrdersDetailBean ordersDetailBean = GsonUtils.getObject(ordersDetailEvent.getResult(), OrdersDetailBean.class);
        final OrdersDetailBean.DataBean data = ordersDetailBean.getData();
        mTvOrderNumber.setText("订单号:" + data.getOrder_sn());
        mTvReducePrice.setText(IVariable.RMB + "-" + data.getConpou());
        mTvTotalPrice.setText("总计:" + data.getTotal_price());
        mTvRoutePrice.setText(IVariable.RMB + data.getPrice());

        String payType = data.getPay_type();
        if (payType.equals("2")){
            tvOrderType.setText("活动订单");
            tvOrderType.setTextColor(getResources().getColor(R.color.otherFf7f6c));
            tvTitle.setText("·活动价格");
            mTvActiveName.setText(getIntent().getStringExtra(IVariable.NAME));
        }else {
            int day = data.getDay();
            CostSettingAdapter costSettingAdapter=new CostSettingAdapter(data.getBasec_price(),this,day);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            mLvPrice.setHasFixedSize(true);
            linearLayoutManager.setSmoothScrollbarEnabled(false);
            mLvPrice.setAdapter(costSettingAdapter);
            mLvPrice.setLayoutManager(linearLayoutManager);
        }

        String payStatus = data.getPay_status();
        if (payStatus.equals("0")){
            ivState.setImageResource(R.drawable.order_wiat_pay);
            tvDes.setText("订单待支付");
            llPayWay.setVisibility(View.GONE);
            btPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrdersDetailActivity.this, ConfirmOrdersActivity.class);
                    intent.putExtra("pay_type", data.getPay_type());
                    intent.putExtra(IVariable.ID, id);
                    startActivity(intent);
                }
            });
        }else if (payStatus.equals("2")){
            ivState.setImageResource(R.drawable.order_cancel_pay);
            tvDes.setText("订单已取消");
            btPay.setVisibility(View.GONE);
            llPayWay.setVisibility(View.GONE);
        }else if (payStatus.equals("1")){
            ivState.setImageResource(R.drawable.order_had_pay);
            tvDes.setText("订单已支付");
            btPay.setVisibility(View.GONE);
            String payName = data.getPay_name();
            if (payName.equals("2")){
                mTvZfb.setText(getString(R.string.activity_confirm_orders_we_chat));
                mTvZfb.setTextColor(Color.parseColor("#39B337"));
                mTvPayDes.setText("微信支付");
            }
        }
    }

}
