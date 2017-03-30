package com.yunspeak.travel.ui.baseui;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import java.util.List;


/**
 * Created by wangyang on 2016/10/7 0007.
 * Toolbar基类
 */

public abstract class BaseBarActivity<T extends ViewDataBinding> extends BaseHideSoftActivity implements IState {


    protected TextView mTvTitle;
    protected Toolbar mToolbar;
    protected T dataBinding;
    protected MenuItem item;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBar();
        createTitle();
        initOptions();
        PushAgent.getInstance(this).onAppStart();
    }

    protected  void initBar() {
        dataBinding = DataBindingUtil.setContentView(this,initLayoutRes());
        mToolbar = (Toolbar) findViewById(R.id.tv_common_bar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
    }

    //设置头部
    protected void createTitle() {
        mTvTitle = (TextView) findViewById(R.id.tv_common_title);
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


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

    protected String getString(EditText editText) {
        if (editText==null)return "";
        return editText.getText().toString().trim();
    }


    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public void hideSoftWore(View view) {
        hideKeyboard(view.getWindowToken());
    }

    /**
     * 设置焦点
     * @param view
     * @param b
     */
    public void setFcouse(View view, boolean b) {
        view.setClickable(b);
        view.setFocusableInTouchMode(b);
        view.setFocusable(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu, menu);
        item = menu.findItem(R.id.action_history);
        item.setTitle(initRightText());
        return true;
    }

    protected String initRightText() {
        return "";
    }

    public void canSmoothInNetScroll(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
