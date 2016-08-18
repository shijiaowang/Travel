package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.PhoneUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/8/15 0015.
 * 注册界面
 */
public class RegisterActivity extends BaseTransActivity implements View.OnClickListener {
   //请求
    private static final int REGISTER_REQ = 0;//注册
    private static final int VERIFICATION_REQ = 1;//验证码

    //错误码
    private static final int VER_ERROR = 0;//验证码错误
    @ViewInject(R.id.et_phone)
    private EditText mEtPhone;
    @ViewInject(R.id.et_password)
    private EditText mEtPassword;
    @ViewInject(R.id.et_re_password)
    private EditText mEtRePassword;
    @ViewInject(R.id.et_ver)
    private EditText mEtVer;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.bt_ver)
    private Button mBtVer;
    private TextView mTvBack;
    private int verTime=60;//验证码时间
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (verTime<=0){
                removeCallbacksAndMessages(null);
                verTime=60;//初始化事件
                mBtVer.setClickable(true);
                mBtVer.setText("重发验证码");
                mBtVer.setBackgroundResource(R.drawable.fragment_find_search_bg);
            }
            mBtVer.setText("重发验证码("+--verTime+")");
            sendEmptyMessageDelayed(0,1000);
        }
    };
    @Override
    protected void initView() {
        FontsIconUtil.findIconFontsById(this,R.id.tv_phone,R.id.tv_password,R.id.tv_re_password,R.id.tv_message);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back,this);
    }

    @Override
    protected void initData() {
       mEtPhone.requestFocus();

    }

    @Override
    protected void initListener() {
        mBtVer.setOnClickListener(this);
        mTvBack.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
    }

    private void toRegister() {
        String ver = mEtVer.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String rePassword = mEtRePassword.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        int length = mEtVer.getText().toString().length();
        if (checkPhoneNumber(phone)) return;
        if (StringUtils.isEmpty(mEtVer.getText().toString().trim())){
            requestAndSetErrorMessage(mEtVer,getString(R.string.ver_is_empty));
            return;
        }
        if (!(length==4 || length==6)){
            requestAndSetErrorMessage(mEtVer,getString(R.string.ver_is_error));
            return;
        }
        if (StringUtils.isEmpty(password)){
            requestAndSetErrorMessage(mEtPassword,getString(R.string.please_enter_password));
            return;
        }
        if (StringUtils.isEmpty(rePassword)){
            requestAndSetErrorMessage(mEtPassword,getString(R.string.please_enter_password));
            return;
        }
        if (!password.equals(rePassword)){
            requestAndSetErrorMessage(mEtPassword,getString(R.string.password_not_consistent));
            return;
        }
        Map<String, String> map = MapUtils.Build().addKey(RegisterActivity.this).add("name", phone).add("pwd", password).add("code", ver).end();
        XEventUtils.postUseCommonBackJson(IVariable.REGISTER_USER, map, REGISTER_REQ);
    }

    /**
     * 检查手机号码
     * @param phone
     * @return
     */
    private boolean checkPhoneNumber(String phone) {
        if (StringUtils.isEmpty(phone)){
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enter_right_11_number));
            return true;
        }
        if (!PhoneUtils.isMobileNO(phone)){
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enterr_right_number_with_11));
            return true;
        }
        return false;
    }

    /**
     * 设置错误信息
     * @param request
     * @param errorMessage
     */
    private void requestAndSetErrorMessage(EditText request, String errorMessage) {
        request.requestFocus();
        String message="<font color=#5cd0c2>"+errorMessage+"</font>";
        request.setError(Html.fromHtml(message));


    }

    /**
     * 发送验证码
     */
    private void sendVerCode() {
        String phone = mEtPhone.getText().toString().trim();
        if (checkPhoneNumber(phone)) return;
        Map<String, String> map = MapUtils.Build().addKey(RegisterActivity.this).add("tel", phone).end();
        XEventUtils.postUseCommonBackJson(IVariable.GET_VERIFICATIO_CODE, map, VERIFICATION_REQ);
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

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
           dealResult(event);
        }
       if (event.getCode()==VER_ERROR){
           requestAndSetErrorMessage(mEtVer,getString(R.string.ver_is_error));
       }
        ToastUtils.showToast(event.getMessage());
    }

    private void dealResult(HttpEvent event) {
        if (event.getType()==REGISTER_REQ){
            // TODO: 2016/8/18 0018 进入完善页面
        }else if (event.getType()==VERIFICATION_REQ){
            mBtVer.setClickable(false);
            mBtVer.setBackgroundResource(R.drawable.button_bg_un_click);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.bt_ver:
              sendVerCode();
                break;
            case R.id.bt_next:
                toRegister();
                break;
        }
    }
}
