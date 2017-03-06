package com.yunspeak.travel.ui.baseui;

import android.view.WindowManager;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public abstract class FullTransparencyActivity extends BaseActivity {


    @Override
    protected int initLayoutRes() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
