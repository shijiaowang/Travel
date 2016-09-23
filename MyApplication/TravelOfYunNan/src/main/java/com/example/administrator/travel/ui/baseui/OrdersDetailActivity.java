package com.example.administrator.travel.ui.baseui;

import android.app.Activity;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/26 0026.
 * 订单详情
 */
public class OrdersDetailActivity extends LoadingBarBaseActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_orders_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return null;
    }

    @Override
    protected String setTitleName() {
        return "订单详情";
    }
}
