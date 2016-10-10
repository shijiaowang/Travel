package com.example.administrator.travel.ui.home.welcome.splash.login.forgetpassword;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.event.RegisterEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.home.welcome.splash.login.LoginNextCommonEvent;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.ui.view.LineEditText;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.PhoneUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by wangyang on 2016/10/2.
 * 忘记密码
 */
public class ForgetPasswordActivity extends LoadingBarBaseActivity<LoginNextCommonEvent> implements AvoidFastButton.AvoidFastOnClickListener, TextWatcher {
    //请求
    private static final int RESET_PASSWORD = 0;//重置
    private static final int VERIFICATION_REQ = 1;//验证码

    //错误码
    private static final int VER_ERROR = 0;//验证码错误
    public static final int REGISTER_SUCCESS = 4;//注册成功

    private boolean isSending = false;//是否发送过验证码
    @ViewInject(R.id.et_phone)
    private LineEditText mEtPhone;
    @ViewInject(R.id.et_password)
    private LineEditText mEtPassword;
    @ViewInject(R.id.et_re_password)
    private LineEditText mEtRePassword;
    @ViewInject(R.id.et_ver)
    private LineEditText mEtVer;
    @ViewInject(R.id.bt_next)
    private AvoidFastButton mBtNext;
    @ViewInject(R.id.bt_ver)
    private AvoidFastButton mBtVer;
    @ViewInject(R.id.tv_back)
    private TextView mTvBack;
    private int verTime = 60;//验证码时间
    private boolean isSendVer = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (verTime <= 0) {
                removeCallbacksAndMessages(null);
                verTime = 60;//初始化事件
                mBtVer.setClickable(true);
                mBtVer.setText("重发验证码");
                mBtVer.setBackgroundResource(R.drawable.fragment_find_search_bg);
                setFcouse(mEtPhone,true);
                isSending = false;
                return;
            }
            mBtVer.setText("重发验证码(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private String phone;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_forget_password;
    }



    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        mEtPhone.requestFocus();
        btIsClick(mBtNext, false);
        btIsClick(mBtVer, false);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "忘记密码";
    }

    @Override
    protected void onSuccess(LoginNextCommonEvent loginNextCommonEvent) {
            dealResult(loginNextCommonEvent);
            ToastUtils.showToast(loginNextCommonEvent.getMessage());
    }

    @Override
    protected void onFail(LoginNextCommonEvent loginNextCommonEvent) {

    }

    @Override
    protected void initEvent() {
        mBtVer.setOnAvoidFastOnClickListener(this);
        mBtNext.setOnAvoidFastOnClickListener(this);
        mEtPhone.addTextChangedListener(this);
        mEtVer.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtRePassword.addTextChangedListener(this);
    }

    private void changePassword() {
        if (!isSendVer) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.not_send_ver));
            return;
        }
        String ver = mEtVer.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rePassword = mEtRePassword.getText().toString().trim();
        int length = mEtVer.getText().toString().length();
        if (PhoneUtils.checkPhoneNumber(phone)) return;
        if (StringUtils.isEmpty(mEtVer.getText().toString().trim())) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_empty));
            return;
        }
        if (!(length == 4 || length == 6)) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_error));
            return;
        }
        if (StringUtils.isEmpty(password)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.please_enter_password));
            return;
        }
        if (StringUtils.isEmpty(rePassword)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.please_enter_password));
            return;
        }
        if (!password.equals(rePassword)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.password_not_consistent));
            return;
        }
        Map<String, String> map = MapUtils.Build().addKey(this).addTel(phone).addNewPassWord(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
        XEventUtils.postUseCommonBackJson(IVariable.RESET_PASSWORD, map, RESET_PASSWORD, new LoginNextCommonEvent());
    }




    /**
     * 发送验证码
     */
    private void sendVerCode() {
        String phone = getString(mEtPhone);
        if (PhoneUtils.checkPhoneNumber(phone)) return;
        Map<String, String> map = MapUtils.Build().addKey(this).addTel(phone).addType("1").end();
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG,map,VERIFICATION_REQ, new LoginNextCommonEvent());
    }





    private void dealResult(LoginNextCommonEvent event) {
        if (event.getType() == RESET_PASSWORD) {
            finish();
        } else if (event.getType() == VERIFICATION_REQ) {
            btIsClick(mBtVer, false);
            setFcouse(mEtPhone,false);
            phone = getString(mEtPhone);
            isSending = true;
            isSendVer = true;
            mHandler.sendEmptyMessage(0);
        } else if (event.getType() == IVariable.TYPE_GET_KEY) {
            dealKey(event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ver:
                if (GlobalUtils.getKey(this) == null) {
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new RegisterEvent());
                    return;
                }
                sendVerCode();
                break;
            case R.id.bt_next:
                changePassword();
                break;
        }
    }

    /**
     * 存储key
     *
     * @param event
     */
    private void dealKey(LoginNextCommonEvent event) {
        Key key = GsonUtils.getObject(event.getResult(), Key.class);
        GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
        ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
        ShareUtil.putInt(this, IVariable.KEY_CODE, key.getCode());
        sendVerCode();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEtPhone.getText().toString().length() == 11 && !isSending) {
            btIsClick(mBtVer, true);
        } else {
            btIsClick(mBtVer, false);
        }
        if ((StringUtils.isEmpty(getString(mEtPhone))) || (StringUtils.isEmpty(getString(mEtVer))) || (StringUtils.isEmpty(getString(mEtPassword))) ||
                (StringUtils.isEmpty(getString(mEtRePassword)))) {
            btIsClick(mBtNext, false);
        } else {
            btIsClick(mBtNext, true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}