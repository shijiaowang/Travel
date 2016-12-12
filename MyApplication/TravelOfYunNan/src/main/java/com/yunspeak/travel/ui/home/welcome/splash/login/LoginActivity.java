package com.yunspeak.travel.ui.home.welcome.splash.login;
import android.content.Intent;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.RegisterActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.RegisterBean;
import com.yunspeak.travel.ui.home.welcome.splash.register.registersuccess.RegisterSuccessActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 登录
 */
public class LoginActivity extends BaseEventBusActivity<LoginEvent> {
    public static final int SPLASH_RESULT=1;//返回
    @BindView(R.id.et_password) LoginEditText mEdPassword;
    @BindView(R.id.et_name) LoginEditText mEdName;
    @BindView(R.id.bt_login) AvoidFastButton mBtLogin;
    @BindView(R.id.bt_register) AvoidFastButton mBtRegister;
    private int tryGetKey=0;
    private String name;
    private String password;
    private boolean isFirstError=true;
    private void goToLogin() {
        Map<String, String> logMap = MapUtils.Build().addKey().add(IVariable.USERNAME, name).add(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode(password))).end();
        XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN,new LoginEvent());
    }

    @Override
    protected void initEvent() {
        ActivityUtils.getInstance().addActivity(this);
        mEdName.setInputType(InputType.TYPE_CLASS_PHONE);
        mBtRegister.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        mBtLogin.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEdName.getString();
                password =mEdPassword.getString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                    ToastUtils.showToast("密码或者用户名为空");
                    return;
                }
                if (StringUtils.isEmpty(GlobalUtils.getKey())){
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new LoginEvent());
                    return;
                }
                setIsProgress(true);
                goToLogin();

            }
        });

    }



    @Override
    protected String initRightText() {
        return "忘记密码";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(IVariable.DATA,true);
        startActivity(intent);
    }

    @Override
    protected void onSuccess(LoginEvent event) {
            if (event.getType()==IVariable.TYPE_GET_KEY ){
                    //处理初次key出问题
                    Key key = GsonUtils.getObject(event.getResult(), Key.class);
                    GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
                    ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
                    goToLogin();
            }else {
                GlobalValue.KEY_VALUE = GlobalUtils.getKey();
                Login object = GsonUtils.getObject(event.getResult(), Login.class);
                UserInfo userInfo = object.getData();
                GlobalValue.userInfo=userInfo;//赋值
                UserUtils.saveUserInfo(userInfo);//序列化
                PushAgent.getInstance(this).addAlias(userInfo.getId(), "CITYOFF_ID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        LogUtils.e("是否成功"+b+"信息"+s);
                    }
                });
                goToHomeActivity(event);
            }
    }

    private void goToHomeActivity(LoginEvent event) {
        Login login = GsonUtils.getObject(event.getResult(), Login.class);
        UserInfo data = login.getData();
        ShareUtil.putString(this,IVariable.SAVE_NAME, data.getName());
        ShareUtil.putString(this,IVariable.SAVE_PWD, data.getPwd());
        setResult(SPLASH_RESULT);
        Intent intent = new Intent(this, HomeActivity.class);

        if (data!=null){
            com.hyphenate.easeui.domain.UserInfo userInfo=new com.hyphenate.easeui.domain.UserInfo();
            userInfo.setId(data.getId());
            userInfo.setNick_name(data.getNick_name());
            userInfo.setUser_img(data.getUser_img());
            DBManager.insertChatUserInfo(userInfo);
        }
        intent.putExtra(IVariable.USER_INFO, data);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onFail(LoginEvent event) {
        if (event.getCode()==IVariable.KEY_ERROR && isFirstError){
            isFirstError=false;//避免无限循环key错误
            if (tryGetKey<3) {
                tryGetKey++;
                XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new LoginEvent());
            }
        }else {
            ToastUtils.showToast(event.getMessage());
            if (event.getCode()==3){//信息未完善，前去完善信息页面
                RegisterBean registerBean = GsonUtils.getObject(event.getResult(), RegisterBean.class);
                Intent intent=new Intent(this, RegisterSuccessActivity.class);
                intent.putExtra(IVariable.USER_ID,registerBean.getData().getUser_id());
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected String initTitle() {
        return "登录";
    }
}
