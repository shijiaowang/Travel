package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.LoginEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.LineEditText;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;


import java.util.Map;



/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class LoginActivity extends BaseTransActivity implements View.OnClickListener, TextWatcher {
    public static final int SPLASH_RESULT=1;//返回
    @ViewInject(R.id.et_password)
    private LineEditText mEdPassword;
    @ViewInject(R.id.et_name)
    private AutoCompleteTextView mEdName;
    @ViewInject(R.id.bt_login)
    private AvoidFastButton mBtLogin;
    private SharedPreferences sharedPreferences;
    private String key;
    private int tryGetKey=0;
    @ViewInject(R.id.tv_back)
    private FontsIconTextView mTvBack;
    private String name;
    private String password;
    private boolean isFirstError=true;



    @Override
    protected void initData() {
       btIsClick(mBtLogin,false);
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
    }

   @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mEdName.addTextChangedListener(this);
        mEdPassword.addTextChangedListener(this);
        mBtLogin.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                name = getString(mEdName);
                password =getString(mEdPassword);
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast("密码或者用户名为空");
                    return;
                }
                goToLogin();

            }
        });

    }

    @Override
    protected int initRes() {
        return R.layout.activity_login;
    }

    private void goToLogin() {
        Map<String, String> logMap = MapUtils.Build().addKey(LoginActivity.this).add(IVariable.USERNAME, name).add(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode(password))).end();
        XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN,new LoginEvent());
    }
    @Subscribe
    public void onEvent(LoginEvent event) {
        ToastUtils.showToast(event.getMessage()+"---这是登录结果解析前的信息");
        if (event.isSuccess()) {
            if (event.getType()==IVariable.TYPE_GET_KEY ){
                if (tryGetKey==0){//只重复尝试一次请求key
                    tryGetKey++;
                    //处理初次key出问题
                    Key key = GsonUtils.getObject(event.getResult(), Key.class);
                    GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
                    ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
                    ShareUtil.putInt(this, IVariable.KEY_CODE, key.getCode());
                    goToLogin();
                }
            }else {
                GlobalValue.KEY_VALUE = GlobalUtils.getKey(this);
                Login object = GsonUtils.getObject(event.getResult(), Login.class);
                UserInfo userInfo = object.getData();
                GlobalValue.userInfo=userInfo;//赋值
                UserUtils.saveUserInfo(userInfo);//序列化
                goToHomeActivity(event);
            }
        } else {
            if (event.getCode()==IVariable.KEY_ERROR && isFirstError){
                isFirstError=false;//避免无限循环key错误
                XEventUtils.getUseCommonBackJson(IVariable.GET_KEY,null,IVariable.TYPE_GET_KEY,new LoginEvent());
            }else {
                ToastUtils.showToast(event.getMessage());
            }
        }
    }

    private void goToHomeActivity(LoginEvent event) {
        Login login = GsonUtils.getObject(event.getResult(), Login.class);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IVariable.SAVE_NAME, login.getData().getName());
        edit.putString(IVariable.SAVE_PWD, login.getData().getPwd());
        if (key != null) {
            edit.putString(IVariable.KEY, key);
        }
        edit.apply();
        setResult(SPLASH_RESULT);
        Intent intent = new Intent(this, HomeActivity.class);
        UserInfo data = login.getData();
        intent.putExtra(IVariable.USER_INFO, data);
        startActivity(intent);

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (StringUtils.isEmpty(getString(mEdName)) || StringUtils.isEmpty(getString(mEdPassword))){
            btIsClick(mBtLogin,false);
        }else {
            btIsClick(mBtLogin,true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
