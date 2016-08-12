package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;


import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.KeyUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.NetworkUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/27 0027.
 * 欢迎页面
 */
public class WelcomeActivity extends FullTransparencyActivity {
    private static final int START_HOME = 0;
    private static final int START_SPLASH = 1;
    private int GO_WHERE_PAGE = -1;
    private boolean isNetWork = false;//缓存登录时是否有网


    private SharedPreferences sharedPreferences;
    private Login.UserInfo userInfo;


    @Override
    protected int initContentRes() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        int code = sharedPreferences.getInt(IVariable.KEY_CODE, -1);
        //获取key
        if (code != IVariable.OK_KEY_CODE) {
            LogUtils.e("状态码错误，开始重新获取");
            String url = IVariable.API_KEY + IVariable.GET_KEY;
            XEventUtils.getUseCommonBackJson(url, null, IVariable.TYPE_GET_KEY);
        } else {
            LogUtils.e("开始验证");
            GlobalValue.KEY_VALUE = sharedPreferences.getString(IVariable.KEY_VALUE, "");
            //验证缓存的登录
            String userName = sharedPreferences.getString(IVariable.SAVE_NAME, "");
            String userPwd = sharedPreferences.getString(IVariable.SAVE_PWD, "");
            if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPwd)) {
                GO_WHERE_PAGE=START_HOME;//去首页，之后会验证是否经过网络验证
                checkNetAndCheckLogin(userName, userPwd);
            } else {

                //缓存信息为空，重新去登录
                GO_WHERE_PAGE = START_SPLASH;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (GO_WHERE_PAGE == START_HOME) {
                    Intent homeIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    homeIntent.putExtra(IVariable.USER_INFO, userInfo);
                    homeIntent.putExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK, isNetWork);
                    startActivity(homeIntent);
                } else {
                    startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
                }
                finish();

            }
        }).start();

    }
    private void checkNetAndCheckLogin(String userName, String userPwd) {
        //网络不可用，且缓存了信息，直接跳入主页
        if (!NetworkUtils.isNetworkConnected(this)) {
            isNetWork=false;
        } else {
            //网络可用验证登录
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put(IVariable.KEY, KeyUtils.getKey(this));
            stringMap.put(IVariable.USERNAME, userName);
            stringMap.put(IVariable.PASSWORD, userPwd);
            String url = IVariable.LOGIN_URL;
            XEventUtils.postUseCommonBackJson(url, stringMap, IVariable.TYPE_POST_LOGIN);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(HttpEvent event) {
        if (event.getType() == IVariable.TYPE_GET_KEY) {
            getKey(event);
        }
        if (event.getType() == IVariable.TYPE_POST_LOGIN) {
            login(event);
        }
    }

    /**
     * 登錄后的結果
     *
     * @param event
     */
    private void login(HttpEvent event) {
        if (event.isSuccess()) {
            if (event.getCode() == IVariable.SUCCESS) {
                String result = event.getResult();
                Gson gson = new Gson();
                Login login = gson.fromJson(result, Login.class);
                userInfo = login.getData();
                isNetWork = true;
            } else {
                GO_WHERE_PAGE = START_SPLASH;
            }
        }
    }

    /**
     * 獲取key后的結果
     */
    private void getKey(HttpEvent event) {
        if (event.isSuccess()) {
            Key key = GsonUtils.getObject(event.getResult(), Key.class);
            GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
            ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
            ShareUtil.putInt(this, IVariable.KEY_CODE, key.getCode());
        } else {
            LogUtils.e(event.getMessage());
        }


        GO_WHERE_PAGE = START_SPLASH;

    }
}
