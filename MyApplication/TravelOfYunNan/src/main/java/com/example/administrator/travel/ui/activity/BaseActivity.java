package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.x;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
            // Translucent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//状态栏完全透明
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
     * @param button
     * @param b
     */
    protected void btIsClick(Button button,boolean b) {
        if (b) {
            button.setBackgroundResource(R.drawable.fragment_find_search_bg);
            button.setClickable(b);
        }else {
            button.setBackgroundResource(R.drawable.button_bg_un_click);
            button.setClickable(b);
        }
    }
    protected String getString(EditText editText){
        return editText.getText().toString().trim();
    }
    /**
     * 设置 错误信息
     * @param request
     * @param errorMessage
     */
    protected void requestAndSetErrorMessage(EditText request, String errorMessage) {
        request.requestFocus();
        String message="<font color=#5cd0c2>"+errorMessage+"</font>";
        request.setError(Html.fromHtml(message));
    }
    public boolean childIsResume=false;
    protected void registerEventBus(Activity activity){
        EventBus.getDefault().register(activity);
        childIsResume=true;
    }
    protected void unregisterEventBus(Activity activity){
        EventBus.getDefault().unregister(activity);
        childIsResume=false;
    }


}
