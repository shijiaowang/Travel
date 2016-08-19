package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.PhoneUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 更改手机号
 */
public class ChangePhoneActivity extends LoadingBarBaseActivity {
    private static final int CHANGE_VER = 1;//更改手机验证码
    @ViewInject(R.id.bt_next)
    private Button mBtNex;
    @ViewInject(R.id.et_phone)
    private EditText mEtPhone;

    @Override
    protected void initContentView() {

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initEvent() {
        mBtNex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhoneUtils.isMobileNO(getString(mEtPhone))) {
                    requestAndSetErrorMessage(mEtPhone, "请输入正确的号码");
                    return;
                }
                Map<String, String> map = MapUtils.Build().addKey(ChangePhoneActivity.this).add(IVariable.TEL, getString(mEtPhone)).end();
                XEventUtils.postUseCommonBackJson(IVariable.GET_VERIFICATIO_CODE, map, 0);
            }

        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtPhone.getText().toString().trim().length() == 11) {
                    btIsClick(mBtNex, true);
                } else {
                    btIsClick(mBtNex, false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onLoad() {
        setIsProgress(false);
    }

    @Override
    protected void initViewData() {
        btIsClick(mBtNex, false);
    }

    @Override
    protected String setTitleName() {
        return "更改绑定";
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerEventBus(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterEventBus(this);
    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            Intent intent = new Intent(ChangePhoneActivity.this, ChangePhoneVerActivity.class);
            intent.putExtra(IVariable.TEL,getString(mEtPhone));
            startActivityForResult(intent, CHANGE_VER);
        }else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHANGE_VER && resultCode==ChangePhoneVerActivity.SUCCESS){
            finish();
        }
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
