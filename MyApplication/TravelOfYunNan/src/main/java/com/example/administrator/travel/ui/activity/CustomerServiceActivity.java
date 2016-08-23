package com.example.administrator.travel.ui.activity;

import android.app.Activity;

import com.example.administrator.travel.R;


/**
 * Created by Administrator on 2016/8/18 0018.
 * 客服中心
 */
public class CustomerServiceActivity extends LoadingBarBaseActivity {

    @Override
    protected int setContentLayout() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return null;
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected String setTitleName() {
        return "客服中心";
    }
}
