package com.yunspeak.travel.ui.me.changephone.bindphone;

import android.text.Editable;
import android.text.InputType;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/6 0006.
 * 绑定新手机
 */

public class BindPhoneActivity extends BaseNetWorkActivity<BindPhoneEvent> {
    @BindView(R.id.et_phone)
    LoginEditText etPhone;
    @BindView(R.id.et_ver)
    LoginEditText etVer;
    @BindView(R.id.bt_next)
    AvoidFastButton btNext;
    private String tel;


    @Override
    protected void initEvent() {
        changeClickAble(btNext, false);
        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        etVer.setInputType(InputType.TYPE_CLASS_TEXT);
        btNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(etVer.getString())) {
                    ToastUtils.showToast("请输入验证码。");
                    return;
                }
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addUserId().addCode(etVer.getString()).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE, end, TYPE_OTHER, new BindPhoneEvent());
            }

        });
        etVer.setOnSendButtonClickListener(new LoginEditText.SendButtonOnClickListener() {
            @Override
            public void onClick(View button) {
                if (PhoneUtils.checkPhoneNumber(etPhone.getString())){
                    ToastUtils.showToast("请输入正确的手机号码格式");
                    etPhone.setError("手机号码格式错误！");
                    return;}
                tel = etPhone.getString();
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addType("2").end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG, end, TYPE_VER_MSG, new BindPhoneEvent());
            }
        });
        etPhone.addTextChangedListener(new LoginEditText.TextChangedListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone.getString()!=null && etPhone.getString().length() == 11) {
                    etVer.setButtonState(true);
                } else {
                    etVer.setButtonState(false);
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
                changeClickAble(btNext, true);
                etVer.setTimeStart();
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
