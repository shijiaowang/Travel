package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.KeyUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class LoginActivity extends FullTransparencyActivity {

    private EditText mEdPassword;
    private EditText mEdName;
    private Button mBtLogin;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;


    @Override
    protected int initContentRes() {
        return  R.layout.activity_login;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdPassword = (EditText) findViewById(R.id.ed_password);
        mBtLogin = (Button) findViewById(R.id.bt_login);
    }

    @Override
    protected void initListener() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mEdName.getText().toString();
                final String password = mEdPassword.getText().toString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast(LoginActivity.this, "密码或者用户名为空");
                    return;
                }
                Map<String,String> logMap=new HashMap<String, String>();
                logMap.put(IVariable.KEY, KeyUtils.getKey(LoginActivity.this));
                logMap.put(IVariable.USERNAME, "18281614311");
                logMap.put(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode("123456")));
                XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN);

            }
        });

    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            GlobalValue.KEY_VALUE = sharedPreferences.getString(IVariable.KEY, "");
            goToHomeActivity(event);
        }else {
           ToastUtils.showToast(this,event.getMessage());
        }
    }

    private void goToHomeActivity(HttpEvent event) {
        Login login = GsonUtils.getObject(event.getResult(), Login.class);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IVariable.SAVE_NAME, login.getData().getName());
        edit.putString(IVariable.SAVE_PWD, login.getData().getPwd());
        edit.apply();
        Intent intent = new Intent(this, HomeActivity.class);
        Login.UserInfo data = login.getData();
        intent.putExtra(IVariable.USER_INFO, data);
        startActivity(intent);
        finish();
    }
    @Override
    protected void initData() {

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
}
