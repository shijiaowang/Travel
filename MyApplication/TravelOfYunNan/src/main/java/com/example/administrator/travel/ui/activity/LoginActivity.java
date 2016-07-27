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
import com.example.administrator.travel.event.VolleyStringEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.VolleyUtils;
import com.google.gson.Gson;

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
                VolleyUtils.LoginStringRequest(requestQueue, sharedPreferences.getString(IVariable.KEY_VALUE, ""), "18281614311", MD5Utils.encode(MD5Utils.encode("123456")));
            }
        });

    }

    public void onEvent(VolleyStringEvent event) {
        if (event.isSuccess()) {
            if (event.getCode() == IVariable.SUCCESS) {
                goToHomeActivity(event);
            } else if (event.getCode() == IVariable.CODE_EXCEPTION) {
                //json解析异常
                ToastUtils.showToast(LoginActivity.this, "未知错误");
            } else {
                ToastUtils.showToast(LoginActivity.this, event.getMessage());
            }
        } else {
           ToastUtils.showToast(this,"网络错误");
        }
    }

    private void goToHomeActivity(VolleyStringEvent event) {
        String result = event.getResult();
        Gson gson = new Gson();
        Login login = gson.fromJson(result, Login.class);
        //保存密码
        sharedPreferences.edit().putString(IVariable.SAVE_NAME, login.getData().getName()).apply();
        sharedPreferences.edit().putString(IVariable.SAVE_PWD, login.getData().getPwd()).apply();
        GlobalValue.KEY_VALUE=sharedPreferences.getString(IVariable.KEY,"");
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
