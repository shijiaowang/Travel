package com.yunspeak.travel.ui.home.welcome.splash.register;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Key;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.RegisterEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseEventBusActivity;
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
import org.greenrobot.eventbus.EventBus;
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
    //错误码
    private static final int VER_ERROR = 0;//验证码错误
    public static final int REGISTER_SUCCESS = 4;//注册成功
    @BindView(R.id.et_phone) LoginEditText mEtPhone;
    @BindView(R.id.et_password) LoginEditText mEtPassword;
    @BindView(R.id.et_re_password) LoginEditText mEtRePassword;
    @BindView(R.id.et_ver) LoginEditText mEtVer;
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    private boolean isSendVer = false;
    private int tryGetKey;
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
        Map<String, String> map = MapUtils.Build().addKey().addUserName(phone).addPassword(MD5Utils.encode(MD5Utils.encode(password))).add(IVariable.CODE, ver).end();
        XEventUtils.postUseCommonBackJson(IVariable.REGISTER_USER, map, REGISTER_REQ,new RegisterEvent());
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
        Map<String, String> map = MapUtils.Build().addKey().add(IVariable.TEL, phone).end();
        XEventUtils.postUseCommonBackJson(IVariable.GET_VERIFICATIO_CODE, map, VERIFICATION_REQ,new RegisterEvent());
    }







    @Override
    protected void onFail(RegisterEvent event) {
        if ((event.getType() == IVariable.TYPE_GET_KEY  || event.getCode()==-1)&& tryGetKey<3){
            tryGetKey++;
            XEventUtils.getUseCommonBackJson(IVariable.GET_KEY, null, IVariable.TYPE_GET_KEY,new RegisterEvent());
        }else  if (event.getCode() == VER_ERROR) {
            requestAndSetErrorMessage(mEtVer, getString(R.string.ver_is_error));
        }else if(event.getCode()==-1){
            ToastUtils.showToast("key错误，正在重新获取！请保持网络畅通");
        }else if (!NetworkUtils.isNetworkConnected(this)){
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
        ActivityUtils.getInstance().addActivity(this);
        mEtPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtVer.setInputType(InputType.TYPE_CLASS_PHONE);
        mEtPhone.requestFocus();
        changeClickAble(mBtNext, false);
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


    @Override
    protected void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
        if (!imm.isActive()) imm.showSoftInput(mEtPhone,InputMethodManager.SHOW_FORCED);
    }

    private void dealResult(RegisterEvent event) {
        if (event.getType() == REGISTER_REQ) {
            RegisterBean register = GsonUtils.getObject(event.getResult(), RegisterBean.class);
            RegisterBean.DataBean data = register.getData();
            PushAgent.getInstance(this).addAlias(data.getUser_id(), "YUNS_ID", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean b, String s) {
                    LogUtils.e("是否成功"+b+"信息"+s);
                }
            });
            Intent intent = new Intent();
            intent.putExtra(IVariable.USER_ID, register.getData().getUser_id());
            setResult(REGISTER_SUCCESS, intent);
            finish();
        } else if (event.getType() == VERIFICATION_REQ) {
            isSendVer = true;
        } else if (event.getType() == IVariable.TYPE_GET_KEY) {
            dealKey(event);
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
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)){

        }
    }

    @Override
    protected String initTitle() {
        return "注册";
    }
}
