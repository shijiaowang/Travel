package com.yunspeak.travel.ui.me.changephone;
import android.content.Intent;
import android.text.InputType;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.changephone.bindphone.BindPhoneActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/19 0019.
 * 更改手机号
 */
public class ChangePhoneActivity extends BaseNetWorkActivity<ChangePhoneEvent> {
    @BindView(R.id.et_phone)
    LoginEditText etPhone;
    @BindView(R.id.et_ver)
    LoginEditText etVer;
    @BindView(R.id.bt_next)
    AvoidFastButton btNext;
    private String tel;
    private boolean isSend;


    @Override
    protected void initEvent() {
        UserInfo userInfo = GlobalUtils.getUserInfo();
        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        etPhone.hideDeleteButton();
        etVer.setInputType(InputType.TYPE_CLASS_TEXT);
        try {
            etPhone.setPhoneText(userInfo.getTel());
            etVer.setButtonState(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(etVer.getString())) {
                    ToastUtils.showToast("请输入验证码。");
                    return;
                }
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addUserId().addCode(etVer.getString()).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE, end, TYPE_OTHER, new ChangePhoneEvent());
            }

        });
        etVer.setOnSendButtonClickListener(new LoginEditText.SendButtonOnClickListener() {
            @Override
            public void onClick(View button) {
                Map<String, String> end = MapUtils.Build().addKey().addTel(tel).addType("1").end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE_VER_MSG, end, TYPE_VER_MSG, new ChangePhoneEvent());
            }
        });
    }


    @Override
    protected boolean isAutoLoad() {
        setIsProgress(false);
        changeClickAble(btNext, false);
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
                etPhone.setPhoneText(tel);
                etVer.setButtonState(true);
                break;
            case TYPE_VER_MSG:
                //发送验证码
                isSend = true;
                changeClickAble(btNext, true);
                etVer.setTimeStart();
                ToastUtils.showToast("验证码发送成功");
                break;
            case TYPE_OTHER:
                //绑定成功
                startActivityForResult(new Intent(this, BindPhoneActivity.class), REQ_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_CODE) {
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
