package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;


import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.db.DBManager;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.WelcomeEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.NetworkUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;


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
    @ViewInject(R.id.pb_load)
    private View mPbLoad;


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
        RotateAnimation rotateAnimation = new RotateAnimation(0, 8888, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        mPbLoad.startAnimation(rotateAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int code = sharedPreferences.getInt(IVariable.KEY_CODE, -1);
        //获取key
        if (code != IVariable.OK_KEY_CODE) {
            XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new WelcomeEvent());
        } else {
            GlobalValue.KEY_VALUE = sharedPreferences.getString(IVariable.KEY_VALUE, "");
            //验证缓存的登录
            String userName = sharedPreferences.getString(IVariable.SAVE_NAME, "");
            String userPwd = sharedPreferences.getString(IVariable.SAVE_PWD, "");
            if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPwd)) {
                GO_WHERE_PAGE = START_HOME;//去首页，之后会验证是否经过网络验证
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
                    //初始化数据表
                    if(!DBManager.cityDBIsExits()){
                        DBManager.initCityDB(WelcomeActivity.this);
                    }
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (GO_WHERE_PAGE == START_HOME) {
                    Intent homeIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
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
            isNetWork = false;
        } else {
            //网络可用验证登录
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put(IVariable.KEY, GlobalUtils.getKey(this));
            stringMap.put(IVariable.USERNAME, userName);
            stringMap.put(IVariable.PASSWORD, userPwd);
            String url = IVariable.LOGIN_URL;
            XEventUtils.postUseCommonBackJson(url, stringMap, IVariable.TYPE_POST_LOGIN, new WelcomeEvent());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(WelcomeEvent event) {
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
        LogUtils.e(event.getMessage() + event.getCode());
        if (event.isSuccess()) {
            isNetWork = true;
            GO_WHERE_PAGE = START_HOME;
        } else {
            isNetWork=false;
            GO_WHERE_PAGE = event.getCode() == 0 ? START_SPLASH : START_HOME;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPbLoad.clearAnimation();
    }
}
