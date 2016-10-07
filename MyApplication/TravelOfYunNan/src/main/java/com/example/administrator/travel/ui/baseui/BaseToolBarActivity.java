package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/7 0007.
 * Toolbar基类
 */

public abstract class BaseToolBarActivity<T extends HttpEvent> extends AppCompatActivity {
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.tv_title)TextView mTvTitle;
    @BindView(R.id.tool_bar) Toolbar mToolbar;
    private Activity activity;
    private LayoutInflater inflater;
    private View childView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_toolbar);
        inflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
       if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        childView = inflater.inflate(initLayoutRes(), mFlContent, false);
        mFlContent.addView(childView);
        ButterKnife.bind(childView);
        initListener();
        activity = initDataAndRegisterEventBus();
        if (activity!=null){
            registerEventBus(activity);
        }
        mTvTitle.setText(initTitle());

    }





    /**
     * 初始化布局文件
     * @return
     */
    protected abstract int initLayoutRes();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract Activity initDataAndRegisterEventBus();

    /**
     * 初始化标题
     * @return
     */
    protected abstract String initTitle();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                otherOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 其他按钮
     * @param item
     */
    protected void otherOptionsItemSelected(MenuItem item) {

    }

    /**
     * 注册EventBus
     * @param activity
     */
    protected void registerEventBus(Activity activity) {
        if (!EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().register(activity);
        }
    }
    /**
     * 解除EventBus
     * @param activity
     */
    protected void unregisterEventBus(Activity activity) {
        if (EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().unregister(activity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activity!=null){
            unregisterEventBus(activity);
        }
    }

}
