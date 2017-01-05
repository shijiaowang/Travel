package com.yunspeak.travel.ui.home.welcome.splash.register;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.bean.RegisterBean;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.RegisterEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;
import com.yunspeak.travel.ui.home.welcome.splash.register.registersuccess.RegisterSuccessActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/15 0015.
 * 注册界面
 */
public class RegisterActivity extends BaseEventBusActivity<RegisterEvent> implements View.OnClickListener, AvoidFastButton.AvoidFastOnClickListener, LoginEditText.TextChangedListener {
    //请求
    private static final int REGISTER_REQ = 0;//注册
    private static final int VERIFICATION_REQ = 1;//验证码
    private static final int RESET_PASSWORD = 2;//重置密码
    //错误码
    private static final int VER_ERROR = 0;//验证码错误
    public static final int REGISTER_SUCCESS = 4;//注册成功
    @BindView(R.id.et_phone) LoginEditText mEtPhone;
    @BindView(R.id.et_password) LoginEditText mEtPassword;
    @BindView(R.id.et_re_password) LoginEditText mEtRePassword;
    @BindView(R.id.et_ver) LoginEditText mEtVer;
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    @BindView(R.id.tv_appoint)
    TextView mTvAppoint;
    private boolean isSendVer = false;
    private int tryGetKey;
    private boolean isForget;

    private void toRegister() {
        if (!isSendVer) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.not_send_ver));
            return;
        }
        String ver = mEtVer.getString();
        String password = mEtPassword.getString();
        String rePassword = mEtRePassword.getString();
        String phone = mEtPhone.getString();
        int length = mEtVer.getString().length();
        if (checkPhoneNumber(phone)) return;
        if (StringUtils.isEmpty(mEtVer.getString())) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_empty));
            return;
        }
        if (!(length == 4 || length == 6)) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_error));
            return;
        }
        if (StringUtils.isEmpty(password)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.please_enter_password));
            return;
        }
        if (StringUtils.isEmpty(rePassword)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.please_enter_password));
            return;
        }
        if (!password.equals(rePassword)) {
            requestAndSetErrorMessage(mEtPassword, getString(R.string.password_not_consistent));
            return;
        }
        if (isForget){
            Map<String, String> resetMap = MapUtils.Build().addKey().addTel(phone).addNewPassWord(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
            XEventUtils.postUseCommonBackJson(IVariable.RESET_PASSWORD, resetMap,RESET_PASSWORD, new RegisterEvent());
        }else {
            Map<String, String> map = MapUtils.Build().addKey().addUserName(phone).addPassword(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
            XEventUtils.postUseCommonBackJson(IVariable.REGISTER_USER, map, REGISTER_REQ, new RegisterEvent());
        }

    }

    /**
     * 检查手机号码
     *
     * @param phone
     * @return
     */
    private boolean checkPhoneNumber(String phone) {
        if (StringUtils.isEmpty(phone)) {
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enter_right_11_number));
            return true;
        }
        if (!PhoneUtils.isMobileNO(phone)) {
            requestAndSetErrorMessage(mEtPhone, getString(R.string.enterr_right_number_with_11));
            return true;
        }
        return false;
    }


    /**
     * 发送验证码
     */
    private void sendVerCode() {
        String phone = mEtPhone.getString();
        if (checkPhoneNumber(phone)) return;
        Map<String, String> map = MapUtils.Build().addKey().add(IVariable.TEL, phone).addType("1").end();
        String url=isForget?IVariable.CHANGE_PHONE_VER_MSG:IVariable.GET_VERIFICATIO_CODE;
        XEventUtils.postUseCommonBackJson(url, map, VERIFICATION_REQ,new RegisterEvent());
    }
    @Override
    protected void onFail(RegisterEvent event) {
        if ((event.getType() == IVariable.TYPE_GET_KEY  || event.getCode()==-1)&& tryGetKey<3){
            tryGetKey++;
            XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY,new RegisterEvent());
        }else  if (event.getType()==RESET_PASSWORD || event.getType()==REGISTER_REQ) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_error));
        }else if(event.getCode()==-1 && event.getType()!=IVariable.TYPE_GET_KEY){
            ToastUtils.showToast("key错误，正在重新获取！请保持网络畅通");
            XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY,new RegisterEvent());
        }else if (!NetworkUtils.isNetworkConnected()){
            ToastUtils.showToast("网络错误，请检查网络是否畅通！");
        }else {
            ToastUtils.showToast(event.getMessage());
        }
    }

    @Override
    protected void onSuccess(RegisterEvent registerEvent) {
        dealResult(registerEvent);
    }

    @Override
    protected void initEvent() {
        isForget = getIntent().getBooleanExtra(IVariable.DATA,false);
        mTvAppoint.setVisibility(isForget?View.GONE:View.VISIBLE);
        ActivityUtils.getInstance().addActivity(this);
        mEtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtVer.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtPhone.requestFocus();
        changeClickAble(mBtNext, false);
        String text="点击下一步即表示同意《城外旅游软件许可及服务协议》";
        SpannableString spannableString=new SpannableString(text);
        int startIndex=text.indexOf("《");
        spannableString.setSpan(new CityoffSpeak(this),startIndex,text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvAppoint.setText(spannableString);
        mTvAppoint.setMovementMethod(LinkMovementMethod.getInstance());
        mBtNext.setOnAvoidFastOnClickListener(this);
        mEtPhone.addTextChangedListener(this);
        mEtVer.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtRePassword.addTextChangedListener(this);
        mEtVer.setOnSendButtonClickListener(new LoginEditText.SendButtonOnClickListener() {
            @Override
            public void onClick(View button) {
                if (StringUtils.isEmpty(GlobalUtils.getKey())) {
                    XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY,new RegisterEvent());
                    return;
                }
                sendVerCode();
            }
        });
    }




    private void dealResult(RegisterEvent event) {
        if (event.getType() == REGISTER_REQ) {
            ToastUtils.showToast(event.getMessage());
            if (event.getCode()==2){
                return;
            }
            RegisterBean register = GsonUtils.getObject(event.getResult(), RegisterBean.class);
            RegisterBean.DataBean data = register.getData();
            PushAgent.getInstance(this).addAlias(data.getUser_id(), "CITYOFF_ID", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean b, String s) {
                    LogUtils.e("是否成功"+b+"信息"+s);
                }
            });
            Intent intent = new Intent(this, RegisterSuccessActivity.class);
            intent.putExtra(IVariable.USER_ID, register.getData().getUser_id());
            startActivity(intent);
            //setResult(REGISTER_SUCCESS, intent);
            finish();
        } else if (event.getType() == VERIFICATION_REQ) {
            if (event.getCode()==2){
                ToastUtils.showToast(event.getMessage());
                return;
            }
            isSendVer = true;
            mEtVer.setTimeStart();//开始计时
            ToastUtils.showToast("验证码发送成功");
        } else if (event.getType() == IVariable.TYPE_GET_KEY) {
            dealKey(event);
        }else if (event.getType()==RESET_PASSWORD){
            ToastUtils.showToast("修改成功");
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                toRegister();
                break;
        }
    }

    /**
     * 存储key
     *
     * @param event
     */
    private void dealKey(HttpEvent event) {
        Key key = GsonUtils.getObject(event.getResult(), Key.class);
        GlobalValue.KEY_VALUE = MD5Utils.encode(MD5Utils.encode(key.getData().getValue()));
        ShareUtil.putString(this, IVariable.KEY_VALUE, GlobalValue.KEY_VALUE);
        ShareUtil.putInt(this, IVariable.KEY_CODE, key.getCode());
        sendVerCode();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEtPhone.getString().length() == 11) {
           mEtVer.setButtonState(true);
        } else {
            mEtVer.setButtonState(false);
        }
        if ((StringUtils.isEmpty(mEtPhone.getString())) || (StringUtils.isEmpty(mEtVer.getString())) || (StringUtils.isEmpty(mEtPassword.getString())) ||
                (StringUtils.isEmpty(mEtRePassword.getString()))) {
            changeClickAble(mBtNext, false);
        } else {
            changeClickAble(mBtNext, true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_register;
    }



    @Override
    protected String initTitle() {
        return isForget?"忘记密码":"注册";
    }
}
