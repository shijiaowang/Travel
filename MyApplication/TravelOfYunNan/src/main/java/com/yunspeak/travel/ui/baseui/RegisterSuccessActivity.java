package com.yunspeak.travel.ui.baseui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.event.RegisterSuccessEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LineEditText;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;



/**
 * Created by Administrator on 2016/8/18 0018.
 * 注册成功页面
 */
public class RegisterSuccessActivity extends BaseTransActivity implements View.OnClickListener, AvoidFastButton.AvoidFastOnClickListener {
    public static final int SPLASH_RESULT = 1;//返回

    @ViewInject(R.id.bt_start)
    private AvoidFastButton mBtStart;
    @ViewInject(R.id.et_nick_name)
    private LineEditText mEtNickName;
    @ViewInject(R.id.rl_girl)
    private RelativeLayout mRlGirl;
    @ViewInject(R.id.rb_girl)
    private RadioButton mRbGirl;
    @ViewInject(R.id.rl_boy)
    private RelativeLayout mRlBoy;
    @ViewInject(R.id.rb_boy)
    private RadioButton mRbBoy;
    private String user_id;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        user_id = getIntent().getStringExtra(IVariable.USER_ID);
        btIsClick(mBtStart, false);
    }

    @Override
    protected void initListener() {
        mBtStart.setOnAvoidFastOnClickListener(this);
        mRlBoy.setOnClickListener(this);
        mRlGirl.setOnClickListener(this);
        mEtNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               setClicked();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int initRes() {
        return R.layout.activity_register_success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                perfectInformation();
                break;
            default:
                selectSex(v.getId());
                break;
        }
    }

    private void selectSex(int id) {
        setClicked();
        switch (id) {
            case R.id.rl_girl:
                mRbGirl.setChecked(true);
                mRbBoy.setChecked(false);
                return;
            case R.id.rl_boy:
                mRbBoy.setChecked(true);
                mRbGirl.setChecked(false);
                break;
        }
    }

    private void setClicked() {
        if (!(StringUtils.isEmpty(mEtNickName.getText().toString().trim())) && (mRbBoy.isChecked() || mRbBoy.isChecked())){
            btIsClick(mBtStart,true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 完善信息
     */
    private void perfectInformation() {
        String nickName = mEtNickName.getText().toString().trim();
        if (StringUtils.isEmpty(nickName)) {
            requestAndSetErrorMessage(mEtNickName, getString(R.string.please_enter_nick_name));
            return;
        }
        if (StringUtils.isEmpty(user_id)) {
            return;
        }
        if (!(mRbGirl.isChecked() || mRbBoy.isChecked())) {
            ToastUtils.showToast(getString(R.string.please_select_your_sex));
            return;
        }

        Map<String, String> infoMap = MapUtils.Build().addKey(RegisterSuccessActivity.this).add(IVariable.USER_ID, user_id).add(IVariable.SEX,mRbBoy.isChecked()?"1":"0").add(IVariable.NICK_NAME, nickName).end();
        XEventUtils.postUseCommonBackJson(IVariable.PERFECT_INFORMATION, infoMap, IVariable.TYPE_REGISTER_USER,new RegisterSuccessEvent());

    }
    @Subscribe
    public void onEvent(RegisterSuccessEvent event) {
        if (event.isSuccess()) {
            dealData(event);
        }
        ToastUtils.showToast(event.getMessage());
    }

    private void dealRegister(HttpEvent event) {
        //序列化保存信息
        setResult(SPLASH_RESULT);
        Login object = GsonUtils.getObject(event.getResult(), Login.class);
        UserInfo userInfo = object.getData();
        GlobalValue.userInfo = userInfo;//赋值
        UserUtils.saveUserInfo(userInfo);//序列化
        ShareUtil.putString(this, IVariable.SAVE_NAME, userInfo.getName());
        ShareUtil.putString(this, IVariable.SAVE_PWD, userInfo.getPwd());
        if (GlobalValue.KEY_VALUE != null) {
            ShareUtil.putString(this, IVariable.KEY, GlobalValue.KEY_VALUE);
        }
        gotoHomeActivity();
    }

    private void dealData(HttpEvent event) {
        switch (event.getType()) {
            case IVariable.TYPE_REGISTER_USER:
                dealRegister(event);
                break;
        }
    }


    private void gotoHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}
