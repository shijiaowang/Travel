package com.yunspeak.travel.ui.me.changephone.bindphone;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LineEditText;
import com.yunspeak.travel.ui.view.PhoneTextView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/6 0006.
 * 绑定新手机
 */

public class BindPhoneActivity extends BaseNetWorkActivity<BindPhoneEvent> {
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    @BindView(R.id.ptv_phone) PhoneTextView mPtvPhone;
    @BindView(R.id.bt_ver) AvoidFastButton mBtVer;
    @BindView(R.id.et_ver) EditText mEtVer;
    @BindView(R.id.et_phone) LineEditText mEtPhone;
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
    protected void initEvent() {
        btIsClick(mBtNext, false);
        btIsClick(mBtVer, false);
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
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected String initTitle() {
        return "绑定手机";
    }
}
