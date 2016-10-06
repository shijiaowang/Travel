package com.example.administrator.travel.ui.me.changephone.bindphone;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.changepassword.ChangePassWordEvent;
import com.example.administrator.travel.ui.me.changephone.ChangePhoneActivity;
import com.example.administrator.travel.ui.me.changephone.ChangePhoneEvent;
import com.example.administrator.travel.ui.me.changephone.GetPhoneBean;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.ui.view.LineEditText;
import com.example.administrator.travel.ui.view.PhoneTextView;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.PhoneUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by wangyang on 2016/10/6 0006.
 * 绑定新手机
 */

public class BindPhoneActivity extends LoadingBarBaseActivity<BindPhoneEvent> {
    @ViewInject(R.id.bt_next)
    private AvoidFastButton mBtNext;
    @ViewInject(R.id.ptv_phone)
    private PhoneTextView mPtvPhone;
    @ViewInject(R.id.bt_ver)
    private AvoidFastButton mBtVer;
    @ViewInject(R.id.et_ver)
    private EditText mEtVer;
    @ViewInject(R.id.et_phone)
    private LineEditText mEtPhone;
    private boolean isSend = false;
    private int verTime = 60;//验证码时间
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (verTime <= 0) {
                removeCallbacksAndMessages(null);
                verTime = 60;//初始化事件
                mBtVer.setText("重发验证码");
                mEtPhone.setFocusable(true);
                mEtPhone.setClickable(true);
                mEtPhone.setFocusableInTouchMode(true);
                btIsClick(mBtVer, true);
                return;
            }
            mBtVer.setText("重发验证码(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private String tel;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initEvent() {
        UserInfo userInfo = GlobalUtils.getUserInfo();
        try {
            mPtvPhone.setPhoneNumber(userInfo.getTel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBtNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(getString(mEtVer))) {
                    ToastUtils.showToast("请输入验证码。");
                    return;
                }
                Map<String, String> end = MapUtils.Build().addKey(BindPhoneActivity.this).addTel(tel).addUserId().addCode(getString(mEtVer)).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE, end, TYPE_OTHER, new BindPhoneEvent());
            }

        });
        mBtVer.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (PhoneUtils.checkPhoneNumber(getString(mEtPhone))) return;
                Map<String, String> end = MapUtils.Build().addKey(BindPhoneActivity.this).addTel(getString(mEtPhone)).addType("2").end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG, end, TYPE_VER_MSG, new BindPhoneEvent());
            }
        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtPhone.getText().toString().length() == 11) {
                    btIsClick(mBtVer, true);
                } else {
                    btIsClick(mBtVer, false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onLoad(int typeRefresh) {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        btIsClick(mBtNext, false);
        btIsClick(mBtVer, false);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "绑定手机";
    }


    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(BindPhoneEvent changePhoneEvent) {
        switch (changePhoneEvent.getType()) {
            case TYPE_VER_MSG:
                //发送验证码
                btIsClick(mBtVer, false);
                btIsClick(mBtNext, true);
                isSend = true;
                tel = getString(mEtPhone);
                mEtPhone.setFocusable(false);
                mEtPhone.setClickable(false);
                mEtPhone.setFocusableInTouchMode(false);

                ToastUtils.showToast("验证码发送成功");
                mHandler.sendEmptyMessage(0);
                break;
            case TYPE_OTHER:
                //绑定成功
                ToastUtils.showToast(changePhoneEvent.getMessage());
                UserInfo userInfo = GlobalUtils.getUserInfo();
                userInfo.setTel(tel);
                UserUtils.saveUserInfo(userInfo);
                setResult(RESULT_CODE);
                finish();
                break;
        }
    }

    @Override
    protected void onFail(BindPhoneEvent event) {

    }
}
