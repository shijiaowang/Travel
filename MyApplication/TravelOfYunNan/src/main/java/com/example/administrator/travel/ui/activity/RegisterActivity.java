package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.security.PublicKey;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class RegisterActivity extends FullTransparencyActivity {
    private static final int REGISTER_REQ=0;//注册
    private static final int VERIFICATION_REQ=1;//验证码
    @ViewInject(R.id.et_phone)
    private EditText mEtPhone;
    @ViewInject(R.id.et_password)
    private EditText mEtPassword;
    @ViewInject(R.id.et_nike_name)
    private EditText mEtNikeName;
    @ViewInject(R.id.bt_register)
    private Button mBtRegister;
    @ViewInject(R.id.bt_ver)
    private Button mBtVer;

    @Override
    protected int initContentRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBtVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtPhone.getText().toString().trim();
                Map<String, String> map = MapUtils.Build().addKey(RegisterActivity.this).add("tel", phone).end();
                XEventUtils.postUseCommonBackJson(IVariable.GET_VERIFICATIO_CODE, map,VERIFICATION_REQ);
            }
        });
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nikeName = mEtNikeName.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                String phone = mEtPhone.getText().toString().trim();
                Map<String, String> map = MapUtils.Build().addKey(RegisterActivity.this).add("name", phone).add("pwd",password).add("code", nikeName).end();
                XEventUtils.postUseCommonBackJson(IVariable.REGISTER_USER, map,REGISTER_REQ);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            if (event.getCode()==REGISTER_REQ) {
                finish();
            }
        }
        ToastUtils.showToast(event.getMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
