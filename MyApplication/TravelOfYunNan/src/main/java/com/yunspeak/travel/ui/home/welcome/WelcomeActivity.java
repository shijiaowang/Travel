package com.yunspeak.travel.ui.home.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;


import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.WelcomeEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.FullTransparencyActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.home.welcome.splash.SplashActivity;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/27 0027.
 * 欢迎页面
 */
public class WelcomeActivity extends FullTransparencyActivity {
    private static final int START_HOME = 0;
    private static final int START_SPLASH = 1;
    private int GO_WHERE_PAGE = -1;
    private boolean isNetWork = false;//缓存登录时是否有网
    private SharedPreferences sharedPreferences;
    @BindView(R.id.pb_load) View mPbLoad;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        EventBus.getDefault().register(this);
        ActivityUtils.getInstance().addActivity(this);
    }

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
            Map<String, String> stringMap = MapUtils.Build().addKey().addUserName(userName).addPassword(userPwd).end();
            XEventUtils.getUseCommonBackJson(IVariable.LOGIN_URL, stringMap, IVariable.TYPE_POST_LOGIN, new WelcomeEvent());
        }
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
    private void login(WelcomeEvent event) {
        LogUtils.e(event.getMessage() + event.getCode());
        if (event.isSuccess()) {
            isNetWork = true;
            GO_WHERE_PAGE = START_HOME;
            try {
                Login object = GsonUtils.getObject(event.getResult(), Login.class);
                UserInfo data = object.getData();
                UserUtils.saveUserInfo(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        EventBus.getDefault().unregister(this);
    }
}
