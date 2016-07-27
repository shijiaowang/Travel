package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.VolleyStringEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.NetworkUtils;
import com.example.administrator.travel.utils.ShareUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.VolleyUtils;
import com.google.gson.Gson;



import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class WelcomeActivity extends FullTransparencyActivity {
    private static final int START_HOME=0;
    private static final int START_SPLASH=1;
    private int GO_WHERE_PAGE=-1;
    private boolean isNetWork=false;//缓存登录时是否有网



    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private Login.UserInfo userInfo;


    @Override
    protected int initContentRes() {
        return  R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               if (GO_WHERE_PAGE==START_HOME){
                   Intent homeIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                   homeIntent.putExtra(IVariable.USER_INFO,userInfo);
                   homeIntent.putExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK,isNetWork);
                   startActivity(homeIntent);
               }else {
                   startActivity(new Intent(WelcomeActivity.this,SplashActivity.class));
               }
               finish();

           }
       }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int code = sharedPreferences.getInt(IVariable.KEY_CODE, -1);
        //获取key
        if (code!=IVariable.OK_KEY_CODE){
            String url=IVariable.API_KEY+IVariable.GET_KEY;
            VolleyUtils.getStringRequest(url,requestQueue ,IVariable.TYPE_GET_KEY);
        }else {
            GlobalValue.KEY_VALUE=sharedPreferences.getString(IVariable.KEY_VALUE, "");
            //验证缓存的登录
            String userName = sharedPreferences.getString(IVariable.SAVE_NAME, "");
            String userPwd = sharedPreferences.getString(IVariable.SAVE_PWD, "");
            if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPwd)){
                checkNetAndCheckLogin(userName, userPwd);
            }else {
                //缓存信息为空，重新去登录
                GO_WHERE_PAGE=START_SPLASH;
            }
        }

    }

    private void checkNetAndCheckLogin(String userName, String userPwd) {
        //网络不可用，且缓存了信息，直接跳入主页
        if (!NetworkUtils.isNetworkConnected(this)){
            GO_WHERE_PAGE=START_HOME;
        }else {
            VolleyUtils.LoginStringRequest(requestQueue, sharedPreferences.getString(IVariable.KEY_VALUE, ""), userName, userPwd);
            isNetWork=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(VolleyStringEvent event) {
        if (event.getType()==IVariable.TYPE_GET_KEY) {
            getKey(event);
        }
        if (event.getType()==IVariable.TYPE_POST_LOGIN){
            login(event);
        }
    }

    /**
     * 登錄后的結果
     * @param event
     */
    private void login(VolleyStringEvent event) {
        if (event.isSuccess() ){
            if (event.getCode()== IVariable.SUCCESS) {
                String result = event.getResult();
                Gson gson = new Gson();
                Login login = gson.fromJson(result, Login.class);
                userInfo = login.getData();
                GO_WHERE_PAGE=START_HOME;
            }else {
                GO_WHERE_PAGE=START_SPLASH;
            }

        }else {
            LogUtils.e(event.getError().getMessage());
        }
    }
    /**
     *獲取key后的結果
     */
    private void getKey(VolleyStringEvent event) {
            if (event.isSuccess() ){
                if (event.getCode()==IVariable.SUCCESS) {
                    String result = event.getResult();
                    Gson gson = new Gson();
                    Key key = gson.fromJson(result, Key.class);
                    LogUtils.e(key.getData().getValue());
                    ShareUtils.putString(this, IVariable.KEY_VALUE, MD5Utils.encode(MD5Utils.encode(key.getData().getValue())));
                    ShareUtils.putInt(this, IVariable.KEY_CODE, key.getCode());
                }else {
                    LogUtils.e(event.getMessage());
                }

            }else {
                LogUtils.e(event.getError().getMessage());
            }
        GO_WHERE_PAGE=START_SPLASH;

    }
}
