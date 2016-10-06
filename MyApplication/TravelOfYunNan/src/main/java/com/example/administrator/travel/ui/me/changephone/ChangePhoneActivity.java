package com.example.administrator.travel.ui.me.changephone;

import android.app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.EditText;


import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.UserInfo;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.changepassword.ChangePassWordEvent;
import com.example.administrator.travel.ui.me.changephone.bindphone.BindPhoneActivity;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.ui.view.PhoneTextView;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;


/**
 * Created by wangyang on 2016/8/19 0019.
 * 更改手机号
 */
public class ChangePhoneActivity extends LoadingBarBaseActivity<ChangePhoneEvent> {
    @ViewInject(R.id.bt_next)
    private AvoidFastButton mBtNext;
    @ViewInject(R.id.ptv_phone)
    private PhoneTextView mPtvPhone;
    @ViewInject(R.id.bt_ver)
    private AvoidFastButton mBtVer;
    @ViewInject(R.id.et_ver)
    private EditText mEtVer;
    private boolean isSend=false;
    private int verTime = 60;//验证码时间
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (verTime <= 0) {
                removeCallbacksAndMessages(null);
                verTime = 60;//初始化事件
                mBtVer.setText("重发验证码");
                btIsClick(mBtVer,true);
                return;
            }
            mBtVer.setText("重发验证码(" + --verTime + ")");
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private String tel;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_change_phone;
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
                Map<String, String> end = MapUtils.Build().addKey(ChangePhoneActivity.this).addTel(tel).addUserId().addCode(getString(mEtVer)).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE,end,TYPE_OTHER,new ChangePhoneEvent());
            }

        });
        mBtVer.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> end = MapUtils.Build().addKey(ChangePhoneActivity.this).addTel(tel).addType("1").end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG, end, TYPE_VER_MSG, new ChangePhoneEvent());
            }
        });
    }

    @Override
    protected void onLoad(int typeRefresh) {
        setIsProgress(false);
        btIsClick(mBtNext,false);
        Map<String, String> end = MapUtils.Build().addKey(this).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CURRENT_BIND_PHONE, end, TYPE_REFRESH, new ChangePhoneEvent());

    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "解除绑定";
    }


    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(ChangePhoneEvent changePhoneEvent) {
        switch (changePhoneEvent.getType()) {
            case TYPE_REFRESH:
                //获取当前手机号
                GetPhoneBean object = GsonUtils.getObject(changePhoneEvent.getResult(), GetPhoneBean.class);
                tel = object.getData().getTel();
                mPtvPhone.setPhoneNumber(tel);
                break;
            case TYPE_VER_MSG:
                //发送验证码
                isSend=true;
                btIsClick(mBtVer,false);
                btIsClick(mBtNext,true);
                ToastUtils.showToast("验证码发送成功");
                mHandler.sendEmptyMessage(0);
                break;
            case TYPE_OTHER:
                //绑定成功
                startActivityForResult(new Intent(this, BindPhoneActivity.class),REQ_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE){
            finish();
        }
    }

    @Override
    protected void onFail(ChangePhoneEvent event) {

    }
}
