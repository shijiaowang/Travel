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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.administrator.travel.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/7 0007.
 * Toolbar基类
 */

public abstract class BaseToolBarActivity extends AppCompatActivity {
    public static final int TYPE_LIKE_DISCUSS = 0;//点赞请求
    public static final int TYPE_LOAD = 1;//普通读取请求
    public static final int TYPE_DISCUSS = 2;//留言
    public static final int TYPE_REFRESH = 3;//刷新
    public static final int TYPE_DELETE = 4;//删除
    public static final int TYPE_SEARCH = 5;//搜索
    public static final int TYPE_SAVE = 6;//保存
    public static final int TYPE_UPDATE = 7;//更新
    public static final int TYPE_VER_MSG = 8;//发送短信验证
    public static final int TYPE_OTHER = 9;//其他
    public static final int REQ_CODE = 10;
    public static final int RESULT_CODE = 11;
    public static final int TYPE_UP_FILE = 12;//上传文件
    public static final int TYPE_CHANGE = 13;//上传文件


    FrameLayout mFlContent;
    TextView mTvTitle;
    protected Toolbar mToolbar;
    ImageView mIvPageError;//展示错误页面
    ProgressBar mPbLoading;//加载中
    protected LayoutInflater inflater;
    protected View childView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_toolbar);
        inflater = LayoutInflater.from(this);
        mIvPageError = (ImageView) findViewById(R.id.page_error);
        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        mPbLoading.setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_appbar_title);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        childView = inflater.inflate(initLayoutRes(), mFlContent, false);
        mFlContent.addView(childView);
        ButterKnife.bind(this);
        initOptions();
        mTvTitle.setText(initTitle());

    }

    /**
     * 初始化布局文件
     *
     * @return
     */
    protected abstract int initLayoutRes();

    /**
     * 初始化监听等操作
     */
    protected abstract void initOptions();



    /**
     * 初始化标题
     *
     * @return
     */
    protected abstract String initTitle();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
     *
     * @param item
     */
    protected void otherOptionsItemSelected(MenuItem item) {

    }



    /**
     * 网络加载错误
     *
     * @param isShow
     */
    protected void setErrorPage(boolean isShow) {
        mIvPageError.setVisibility(isShow ? View.VISIBLE : View.GONE);
        childView.setVisibility(View.GONE);//隐藏主布局
    }

    /**
     * 加载进度条
     *
     * @param isShow 显示还是隐藏
     */
    protected void setIsProgress(boolean isShow) {
        setIsProgress(isShow, false);
    }

    /**
     * 展示加载进度条
     *
     * @param isShow        显示还是隐藏
     * @param needHideOther 显示时是否需要隐藏其他布局
     */
    protected void setIsProgress(boolean isShow, boolean needHideOther) {
        if (needHideOther) {
            mIvPageError.setVisibility(View.GONE);
            childView.setVisibility(View.GONE);
        }
        mPbLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    /**
     * 注册EventBus
     *
     * @param activity
     */
    protected void registerEventBus(Activity activity) {
        if (!EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().register(activity);
        }
    }

    /**
     * 解除EventBus
     *
     * @param activity
     */
    protected void unregisterEventBus(Activity activity) {
        if (EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().unregister(activity);
        }
    }

    /**
     * 获取集合大小
     *
     * @param list
     * @return
     */
    protected int getListSize(List list) {
        if (list == null) return 0;
        return list.size();
    }

}
