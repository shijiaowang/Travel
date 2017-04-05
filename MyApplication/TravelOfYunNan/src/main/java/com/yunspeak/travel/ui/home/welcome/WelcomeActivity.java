package com.yunspeak.travel.ui.home.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.User;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.ChatDao;
import com.yunspeak.travel.db.CityDao;
import com.yunspeak.travel.db.UserDao;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.WelcomeEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.FullTransparencyActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.home.welcome.splash.login.LoginActivity;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import simpledao.cityoff.com.easydao.BaseDaoFactory;


/**
 * Created by wangyang on 2016/7/27 0027.
 * 欢迎页面
 */
public class WelcomeActivity extends FullTransparencyActivity {
    private static final int START_HOME = 0;
    private static final int START_LOGIN = 1;
    private int GO_WHERE_PAGE = -1;
    private boolean isNetWork = false;//缓存登录时是否有网


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

}

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String code = ShareUtil.getString(this,IVariable.KEY_VALUE, "");
        //获取key
        if (StringUtils.isEmpty(code)) {
            XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new WelcomeEvent());
        } else {
            GlobalValue.KEY_VALUE = ShareUtil.getString(this,IVariable.KEY_VALUE, "");
            UserInfo currentUser = UserUtils.getUserInfo();
            //验证缓存的登录
            if (currentUser!=null && !StringUtils.isEmpty(currentUser.getName()) && !StringUtils.isEmpty(currentUser.getPwd())) {
                GO_WHERE_PAGE = START_HOME;//去首页，之后会验证是否经过网络验证
                checkNetAndCheckLogin(currentUser.getName(), currentUser.getPwd());
            } else {
                GO_WHERE_PAGE = START_LOGIN;
            }
        }
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                //初始化城市数据
                CityDao daoHelper = BaseDaoFactory.getInstance().getDaoHelper(CityDao.class, CityNameBean.class);
                daoHelper.init(UIUtils.getContext());
                if (GO_WHERE_PAGE == START_HOME) {
                    Intent homeIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    homeIntent.putExtra(IVariable.CACHE_LOGIN_ARE_WITH_NETWORK, isNetWork);
                    startActivity(homeIntent);
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        },1500);
    }





    private void checkNetAndCheckLogin(String userName, String userPwd) {
        //网络不可用，且缓存了信息，直接跳入主页
        if (!NetworkUtils.isNetworkConnected()) {
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
            Login object = GsonUtils.getObject(event.getResult(), Login.class);
            UserUtils.saveUserInfo(object.getData());
        } else {
            isNetWork = false;
            GO_WHERE_PAGE = event.getCode() == 0 ? START_LOGIN : START_HOME;

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
        } else {
            LogUtils.e(event.getMessage());
        }
        GO_WHERE_PAGE = START_LOGIN;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
