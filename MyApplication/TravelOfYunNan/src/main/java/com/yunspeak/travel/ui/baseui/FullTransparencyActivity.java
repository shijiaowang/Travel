package com.yunspeak.travel.ui.baseui;

import android.view.View;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public abstract class FullTransparencyActivity extends BaseActivity {


    @Override
    protected int initLayoutRes() {
        getWindow().addFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        return initContentRes();
    }

    protected abstract int initContentRes();

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
