package com.yunspeak.travel.ui.home.welcome.splash.register;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.Register;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.RegisterEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseTransActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LineEditText;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;



/**
 * Created by wangyang on 2016/8/15 0015.
 * 注册界面
 */
public class RegisterActivity extends BaseTransActivity implements View.OnClickListener, TextWatcher, AvoidFastButton.AvoidFastOnClickListener {
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
                mEtPhone.setFocusable(true);
                mEtPhone.setClickable(true);
                mEtPhone.setFocusableInTouchMode(true);
                return;
            }
            mBtVer.setText("重发验证码(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mEtPhone.requestFocus();
        btIsClick(mBtNext, false);
        btIsClick(mBtVer, false);
    }

    @Override
    protected void initListener() {
        mBtVer.setOnAvoidFastOnClickListener(this);
        mBtNext.setOnAvoidFastOnClickListener(this);
        mTvBack.setOnClickListener(this);
        mEtPhone.addTextChangedListener(this);
        mEtVer.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtRePassword.addTextChangedListener(this);
    }

    private void toRegister() {
        if (!isSendVer) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.not_send_ver));
            return;
        }
        String ver = mEtVer.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rePassword = mEtRePassword.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        int length = mEtVer.getText().toString().length();
        if (checkPhoneNumber(phone)) return;
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
        Map<String, String> map = MapUtils.Build().addKey(this).addUserName(phone).addPassword(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
        XEventUtils.postUseCommonBackJson(IVariable.REGISTER_USER, map, REGISTER_REQ,new RegisterEvent());
    }

    /**
     * 检查手机号码
     *
     * @param phone
     * @return
     */
    private boolean checkPhoneNumber(String phone) {
        if (StringUtils.isEmpty(phone)) {
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enter_right_11_number));
            return true;
        }
        if (!PhoneUtils.isMobileNO(phone)) {
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enterr_right_number_with_11));
            return true;
        }
        return false;
    }


    /**
     * 发送验证码
     */
    private void sendVerCode() {
        String phone = mEtPhone.getText().toString().trim();
        if (checkPhoneNumber(phone)) return;
        Map<String, String> map = MapUtils.Build().addKey(RegisterActivity.this).add(IVariable.TEL, phone).end();
        XEventUtils.postUseCommonBackJson(IVariable.GET_VERIFICATIO_CODE, map, VERIFICATION_REQ,new RegisterEvent());
    }

    @Override
    protected int initRes() {
        return R.layout.activity_register;
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void onEvent(RegisterEvent event) {
        if (event.isSuccess()) {
            dealResult(event);
        }
        if (event.getCode() == VER_ERROR) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_error));
        }
        ToastUtils.showToast(event.getMessage());
    }

    private void dealResult(HttpEvent event) {
        if (event.getType() == REGISTER_REQ) {
            Register register = GsonUtils.getObject(event.getResult(), Register.class);
            Intent intent = new Intent();
            intent.putExtra(IVariable.USER_ID, register.getData().getUser_id());
            setResult(REGISTER_SUCCESS, intent);
            finish();
        } else if (event.getType() == VERIFICATION_REQ) {
            btIsClick(mBtVer, false);
            isSending = true;
            isSendVer = true;
            mEtPhone.setFocusable(false);
            mEtPhone.setClickable(false);
            mEtPhone.setFocusableInTouchMode(false);
            mHandler.sendEmptyMessage(0);

        } else if (event.getType() == IVariable.TYPE_GET_KEY) {
            dealKey(event);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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
                sendVerCode();
                break;
            case R.id.bt_next:
                toRegister();
                break;
        }
    }

    /**
     * 存储key
     *
     * @param event
     */
    private void dealKey(HttpEvent event) {
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


}
