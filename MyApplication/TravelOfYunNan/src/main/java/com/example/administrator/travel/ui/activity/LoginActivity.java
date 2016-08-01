package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.KeyUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    public static final int SPLASH_RESULT=1;//返回
    private EditText mEdPassword;
    private AutoCompleteTextView mEdName;
    private Button mBtLogin;
    private SharedPreferences sharedPreferences;
    private String key;
    private int tryGetKey=0;
    private TextView mTvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }




    private void initView() {
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
        mEdName = (AutoCompleteTextView) findViewById(R.id.et_name);
        mEdPassword = (EditText) findViewById(R.id.et_password);
        mBtLogin = (Button) findViewById(R.id.bt_login);
    }


    private void initListener() {
        mTvBack.setOnClickListener(this);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mEdName.getText().toString();
                final String password = mEdPassword.getText().toString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast(LoginActivity.this, "密码或者用户名为空");
                    return;
                }
                goToLogin();

            }
        });

    }

    private void goToLogin() {
        Map<String, String> logMap = new HashMap<String, String>();
        key = KeyUtils.getKey(LoginActivity.this);
        logMap.put(IVariable.KEY, key);
        logMap.put(IVariable.USERNAME, "18281614311");
        logMap.put(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode("123456")));
        XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN);
    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            if (event.getType()==IVariable.TYPE_GET_KEY ){
                if (tryGetKey==0){//只重复尝试一次请求key
                    tryGetKey++;
                    goToLogin();
                }
            }else {
                GlobalValue.KEY_VALUE = sharedPreferences.getString(IVariable.KEY, "");
                goToHomeActivity(event);
            }
        } else {
            if (event.getCode()==IVariable.KEY_ERROR){
                XEventUtils.getUseCommonBackJson(IVariable.GET_KEY,null,IVariable.TYPE_GET_KEY);
            }else {
                ToastUtils.showToast(this, event.getMessage());
            }
        }
    }

    private void goToHomeActivity(HttpEvent event) {
        Login login = GsonUtils.getObject(event.getResult(), Login.class);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IVariable.SAVE_NAME, login.getData().getName());
        edit.putString(IVariable.SAVE_PWD, login.getData().getPwd());
        if (key != null) {
            edit.putString(IVariable.KEY, key);
        }
        edit.apply();
        Intent intent = new Intent(this, HomeActivity.class);
        Login.UserInfo data = login.getData();
        intent.putExtra(IVariable.USER_INFO, data);
        startActivity(intent);
        setResult(SPLASH_RESULT);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
