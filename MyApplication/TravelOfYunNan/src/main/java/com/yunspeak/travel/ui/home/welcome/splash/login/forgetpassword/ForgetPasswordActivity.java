package com.yunspeak.travel.ui.home.welcome.splash.login.forgetpassword;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.event.RegisterEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.home.welcome.splash.login.LoginNextCommonEvent;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LineEditText;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import java.util.Map;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/2.
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseEventBusActivity<LoginNextCommonEvent> implements AvoidFastButton.AvoidFastOnClickListener, LoginEditText.TextChangedListener {
    //请求
    private static final int RESET_PASSWORD = 0;//重置
    private static final int VERIFICATION_REQ = 1;//验证码
    //错误码
    @BindView(R.id.et_phone) LoginEditText mEtPhone;
    @BindView(R.id.et_password) LoginEditText mEtPassword;
    @BindView(R.id.et_re_password) LoginEditText mEtRePassword;
    @BindView(R.id.et_ver) LoginEditText mEtVer;
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    private boolean isSendVer = false;
    private String phone;
    private boolean isForget;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
    }


    @Override
    protected void onSuccess(LoginNextCommonEvent loginNextCommonEvent) {
            dealResult(loginNextCommonEvent);
            ToastUtils.showToast(loginNextCommonEvent.getMessage());
    }

    @Override
    protected void onFail(LoginNextCommonEvent loginNextCommonEvent) {
        ToastUtils.showToast(loginNextCommonEvent.getMessage());
    }



    @Override
    protected void initEvent() {
        isForget = getIntent().getBooleanExtra(IVariable.DATA, false);
        mEtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        changeClickAble(mBtNext, false);
        mEtPhone.addTextChangedListener(this);
        mEtVer.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtRePassword.addTextChangedListener(this);
        mEtVer.setInputType(InputType.TYPE_CLASS_PHONE);
        mBtNext.setOnAvoidFastOnClickListener(this);
        mEtVer.setOnSendButtonClickListener(new LoginEditText.SendButtonOnClickListener() {
            @Override
            public void onClick(View button) {
                if (GlobalUtils.getKey() == null) {
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY, new RegisterEvent());
                    return;
                }
                sendVerCode();
            }
        });

    }

    private void changePassword() {
        if (!isSendVer) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.not_send_ver));
            return;
        }
        String ver = mEtVer.getString();
        String password = mEtPassword.getString();
        String rePassword = mEtRePassword.getString();
        int length = mEtVer.getString().length();
        if (PhoneUtils.checkPhoneNumber(phone)) return;
        if (StringUtils.isEmpty(mEtVer.getString())) {
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
        Map<String, String> map = MapUtils.Build().addKey().addTel(phone).addNewPassWord(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
        XEventUtils.postUseCommonBackJson(IVariable.RESET_PASSWORD, map, RESET_PASSWORD, new LoginNextCommonEvent());
    }




    /**
     * 发送验证码
     */
    private void sendVerCode() {
        String phone = mEtPhone.getString();
        if (PhoneUtils.checkPhoneNumber(phone)) return;
        Map<String, String> map = MapUtils.Build().addKey().addTel(phone).addType("1").end();
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG,map,VERIFICATION_REQ, new LoginNextCommonEvent());
    }




    private void dealResult(LoginNextCommonEvent event) {
        if (event.getType() == RESET_PASSWORD) {
            finish();
        } else if (event.getType() == VERIFICATION_REQ) {
            phone =mEtPhone.getString();
            isSendVer = true;

        } else if (event.getType() == IVariable.TYPE_GET_KEY) {
            dealKey(event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
    protected int initLayoutRes() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected String initTitle() {
        return "忘记密码";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEtPhone.getString().length() == 11) {
            mEtVer.setButtonState(true);
        } else {
            mEtVer.setButtonState(false);
        }
        if ((StringUtils.isEmpty(mEtPhone.getString())) || (StringUtils.isEmpty(mEtVer.getString())) || (StringUtils.isEmpty(mEtPassword.getString())) ||
                (StringUtils.isEmpty(mEtRePassword.getString()))) {
            changeClickAble(mBtNext, false);
        } else {
            changeClickAble(mBtNext, true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
