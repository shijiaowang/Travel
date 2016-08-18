package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Key;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.view.LineEditText;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/8/18 0018.
 * 注册成功页面
 */
public class RegisterSuccessActivity extends BaseTransActivity implements View.OnClickListener {
    public static final int SPLASH_RESULT = 1;//返回

    @ViewInject(R.id.bt_start)
    private Button mBtStart;
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
        FontsIconUtil.findIconFontsById(this,R.id.tv_nick_name,R.id.tv_sex);
    }

    @Override
    protected void initData() {
        user_id = getIntent().getStringExtra(IVariable.USER_ID);
    }

    @Override
    protected void initListener() {
        mBtStart.setOnClickListener(this);
        mRlBoy.setOnClickListener(this);
        mRlGirl.setOnClickListener(this);
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
            case R.id.rl_girl:
                mRbGirl.setChecked(true);
                mRbBoy.setChecked(false);
                break;
            case R.id.rl_boy:
                mRbBoy.setChecked(true);
                mRbGirl.setChecked(false);
                break;
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
        if (!(mRbGirl.isChecked() || mRbBoy.isChecked())){
            ToastUtils.showToast(getString(R.string.please_select_your_sex));
            return;
        }

            Map<String, String> infoMap = MapUtils.Build().addKey(RegisterSuccessActivity.this).add(IVariable.USER_ID, user_id).add(IVariable.SEX, "1").add(IVariable.NICK_NAME, nickName).end();
            XEventUtils.postUseCommonBackJson(IVariable.PERFECT_INFORMATION, infoMap, IVariable.TYPE_REGISTER_USER);

    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            dealData(event);
        }
        ToastUtils.showToast(event.getMessage());
    }

    private void dealRegister(HttpEvent event) {
        //序列化保存信息
        setResult(SPLASH_RESULT);
        Login object = GsonUtils.getObject(event.getResult(), Login.class);
        Login.UserInfo userInfo = object.getData();
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
        switch (event.getType()){
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
