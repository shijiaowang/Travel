package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;

import com.yunspeak.travel.R;

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
}
