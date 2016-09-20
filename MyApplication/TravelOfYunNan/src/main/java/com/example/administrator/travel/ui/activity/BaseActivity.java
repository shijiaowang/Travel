package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.text.Html;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.util.List;


public abstract class BaseActivity extends FragmentActivity {

    public static final int TYPE_LOAD = 1;//普通读取请求
    public static final int TYPE_REFRESH = 3;//刷新
    public static final int TYPE_SEARCH = 5;//搜索
    public static final int TYPE_DISCUSS = 2;//留言
    public static final int TYPE_LIKE_DISCUSS = 0;//点赞请求
    public static final int TYPE_DELETE = 4;//删除
    public static final  int REQ_CODE=0;
    public static final  int RESULT_CODE=1;

    public static final int TRAFFIC_TYPE =0;//交通方式
    public static final int SEX_TYPE=1;//性别赛选
    public static final int AUTH_TYPE=2;//认证筛选
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Translucent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//状态栏完全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 虚拟按键
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //  window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(initLayoutRes());
        x.view().inject(this);
        initView();
        initListener();
        initData();


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    protected abstract int initLayoutRes();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 设置按钮背景
     *
     * @param button
     * @param b
     */
    protected void btIsClick(Button button, boolean b) {
        if (b) {
            button.setBackgroundResource(R.drawable.fragment_find_search_bg);
            button.setClickable(b);
        } else {
            button.setBackgroundResource(R.drawable.button_bg_un_click);
            button.setClickable(b);
        }
    }

    protected String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 设置 错误信息
     *
     * @param request
     * @param errorMessage
     */
    protected void requestAndSetErrorMessage(EditText request, String errorMessage) {
        request.requestFocus();
        String message = "<font color=#5cd0c2>" + errorMessage + "</font>";
        request.setError(Html.fromHtml(message));
    }

    protected void registerEventBus(Activity activity) {
        if (!EventBus.getDefault().isRegistered(activity)) {
            LogUtils.e("activity的Event注册了");
            EventBus.getDefault().register(activity);
        }
    }

    protected void unregisterEventBus(Activity activity) {
        if (EventBus.getDefault().isRegistered(activity)) {
            LogUtils.e("activity的Event注销了");
            EventBus.getDefault().unregister(activity);
        }
    }

    /**
     * 测量listview的高度
     *
     * @param mListView
     * @return
     */
    protected int measureHeight(ListView mListView) {
        // get ListView adapter
        ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
    }
    /**
     * 隐藏软键盘
     * @param view
     */
    public void hideSoftWore(EditText view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }
    public boolean ListIsEmpty(List list){
        if (list ==null || list.size()==0){
            return true;
        }
        return false;
    }
}
