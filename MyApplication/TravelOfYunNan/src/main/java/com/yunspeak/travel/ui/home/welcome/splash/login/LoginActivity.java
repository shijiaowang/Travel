package com.yunspeak.travel.ui.home.welcome.splash.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.event.LoginEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseTransActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.home.welcome.splash.login.forgetpassword.ForgetPasswordActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.RegisterBean;
import com.yunspeak.travel.ui.home.welcome.splash.register.registersuccess.RegisterSuccessActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.LineEditText;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;


import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wangyang on 2016/7/26 0026.
 * 登录
 */
public class LoginActivity extends BaseTransActivity implements View.OnClickListener,LoginEditText.TextChangedListener {
    public static final int SPLASH_RESULT=1;//返回
    @BindView(R.id.et_password)
    LoginEditText mEdPassword;
    @BindView(R.id.et_name) LoginEditText mEdName;
    @BindView(R.id.bt_login) AvoidFastButton mBtLogin;
    private SharedPreferences sharedPreferences;
    private String key;
    private int tryGetKey=0;
    @BindView(R.id.tv_back) FontsIconTextView mTvBack;
    @BindView(R.id.tv_change_password) TextView mTvChangePassword;
    private String name;
    private String password;
    private boolean isFirstError=true;

    @Override
    protected void initData() {
       btIsClick(mBtLogin,false);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        ActivityUtils.getInstance().addActivity(this);
        sharedPreferences = getSharedPreferences(IVariable.SHARE_NAME, MODE_PRIVATE);
    }

   @Override
    protected void initListener() {
        mTvBack.setOnClickListener(this);
        mEdName.addTextChangedListener(this);
        mEdName.setInputType(InputType.TYPE_CLASS_PHONE);
        mEdPassword.addTextChangedListener(this);
        mTvChangePassword.setOnClickListener(this);
       //1.得到InputMethodManager对象
       InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
         //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
       imm.showSoftInput(mEdName,InputMethodManager.SHOW_FORCED);
        mBtLogin.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEdName.getString();
                password =mEdPassword.getString();
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
        Map<String, String> logMap = MapUtils.Build().addKey().add(IVariable.USERNAME, name).add(IVariable.PASSWORD, MD5Utils.encode(MD5Utils.encode(password))).end();
        XEventUtils.postUseCommonBackJson(IVariable.LOGIN_URL, logMap, IVariable.TYPE_POST_LOGIN,new LoginEvent());
    }
    @Subscribe
    public void onEvent(LoginEvent event) {
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
                GlobalValue.KEY_VALUE = GlobalUtils.getKey();
                Login object = GsonUtils.getObject(event.getResult(), Login.class);
                UserInfo userInfo = object.getData();
                GlobalValue.userInfo=userInfo;//赋值
                UserUtils.saveUserInfo(userInfo);//序列化
                PushAgent.getInstance(this).addAlias(userInfo.getId(), "YUNS_ID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        LogUtils.e("是否成功"+b+"信息"+s);
                    }
                });
                goToHomeActivity(event);
            }
        } else {
            if (event.getCode()==IVariable.KEY_ERROR && isFirstError){
                isFirstError=false;//避免无限循环key错误
                XEventUtils.getUseCommonBackJson(IVariable.GET_KEY,null,IVariable.TYPE_GET_KEY,new LoginEvent());
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_change_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (StringUtils.isEmpty(mEdName.getString()) || StringUtils.isEmpty(mEdPassword.getString())){
            btIsClick(mBtLogin,false);
        }else {
            btIsClick(mBtLogin,true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
