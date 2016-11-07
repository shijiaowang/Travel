package com.yunspeak.travel.ui.me.changephone;

import android.app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.EditText;


import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.me.changephone.bindphone.BindPhoneActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.PhoneTextView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/19 0019.
 * 更改手机号
 */
public class ChangePhoneActivity extends BaseNetWorkActivity<ChangePhoneEvent> {
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    @BindView(R.id.ptv_phone) PhoneTextView mPtvPhone;
    @BindView(R.id.bt_ver) AvoidFastButton mBtVer;
    @BindView(R.id.et_ver) EditText mEtVer;
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
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addUserId().addCode(getString(mEtVer)).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE,end,TYPE_OTHER,new ChangePhoneEvent());
            }

        });
        mBtVer.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addType("1").end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG, end, TYPE_VER_MSG, new ChangePhoneEvent());
            }
        });
    }


    @Override
    protected boolean isAutoLoad() {
        setIsProgress(false);
        btIsClick(mBtNext,false);
        return super.isAutoLoad();
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_CURRENT_BIND_PHONE;
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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected String initTitle() {
        return "解除绑定";
    }
}
