package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import org.xutils.x;

/**
 * Created by Administrator on 2016/8/18 0018.
 * 透明的基类
 */
public abstract class BaseTransActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initRes());
        x.view().inject(this);
        initView();
        initListener();
        initData();

    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract int initRes();
}
