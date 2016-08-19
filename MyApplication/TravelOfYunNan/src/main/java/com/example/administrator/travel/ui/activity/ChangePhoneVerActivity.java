package com.example.administrator.travel.ui.activity;


import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 更改手机号验证码页面
 */
public class ChangePhoneVerActivity extends LoadingBarBaseActivity {
    public static final int SUCCESS = 1;//修改成功
    @ViewInject(R.id.et_ver)
    private EditText mEtVer;
    @ViewInject(R.id.bt_submit)
    private AvoidFastButton mBtSubmit;
    private String phone;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_change_phone_ver;
    }

    @Override
    protected void initEvent() {
        phone = getIntent().getStringExtra(IVariable.TEL);
        mBtSubmit.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
                @Override
                public void onClick (View v){
                if (StringUtils.isEmpty(phone)) {
                    return;
                }
                if (!(getString(mEtVer).length() == 6 || getString(mEtVer).length() == 4)) {
                    requestAndSetErrorMessage(mEtVer, "验证码长度错误");
                    return;
                }
                Map<String, String> changeMap = MapUtils.Build().addKey(ChangePhoneVerActivity.this).add(IVariable.TEL, phone).add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()).add(IVariable.CODE, getString(mEtVer)).end();
                XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PHONE, changeMap, 0);

            }

            }

            );
            mEtVer.addTextChangedListener(new

            TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s,int start, int count, int after){

                }

                @Override
                public void onTextChanged (CharSequence s,int start, int before, int count){
                    if (getString(mEtVer).length() > 0) {
                        btIsClick(mBtSubmit, true);
                    } else {
                        btIsClick(mBtSubmit, false);
                    }
                }

                @Override
                public void afterTextChanged (Editable s){

                }
            }

            );
        }

        @Override
        protected void onLoad () {
            setIsProgress(false);
        }

        @Override
        protected void initViewData () {
            btIsClick(mBtSubmit, false);
        }

        @Override
        protected String setTitleName () {
            return "更改绑定";
        }

        @Override
        protected void onResume () {
            super.onResume();
            registerEventBus(this);
        }

        @Override
        protected void onPause () {
            super.onPause();
            unregisterEventBus(this);
        }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            Login.UserInfo userInfo = GlobalUtils.getUserInfo();
            userInfo.setTel(phone);
            UserUtils.saveUserInfo(userInfo);
            setResult(SUCCESS);
            finish();
        }
        ToastUtils.showToast(event.getMessage());

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
