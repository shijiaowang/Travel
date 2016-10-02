package com.example.administrator.travel.ui.home.login.forgetpassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.bean.Register;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.RegisterEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.home.login.LoginNextCommonEvent;
import com.example.administrator.travel.ui.home.register.RegisterActivity;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.ui.view.LineEditText;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.xutils.view.annotation.ViewInject;
/**
 * Created by wangyang on 2016/10/2.
 * 忘记密码
 */
public class ForgetPasswordActivity extends LoadingBarBaseActivity<LoginNextCommonEvent> implements AvoidFastButton.AvoidFastOnClickListener, TextWatcher {
    //请求
    private static final int REGISTER_REQ = 0;//注册
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
                isSending = false;
                return;
            }
            mBtVer.setText("重发验证码(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };





    private void toChangePassword() {
        String ver = mEtVer.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rePassword = mEtRePassword.getText().toString().trim();
        int length = mEtVer.getText().toString().length();
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

    }








    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.bt_ver:
                if (GlobalUtils.getKey(this) == null) {
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY,new RegisterEvent());
                    return;
                }
                break;
            case R.id.bt_next:

                break;
        }
    }





    @Override
    protected int setContentLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initEvent() {
        mBtVer.setOnAvoidFastOnClickListener(this);
        mBtNext.setOnAvoidFastOnClickListener(this);
        mEtVer.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtRePassword.addTextChangedListener(this);
    }

    @Override
    protected void onLoad(int type) {
           setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "忘记密码";
    }

    @Override
    protected void onSuccess(LoginNextCommonEvent loginNextCommonEvent) {
        ToastUtils.showToast(loginNextCommonEvent.getMessage());
        finish();
    }

    @Override
    protected void onFail(LoginNextCommonEvent loginNextCommonEvent) {

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
