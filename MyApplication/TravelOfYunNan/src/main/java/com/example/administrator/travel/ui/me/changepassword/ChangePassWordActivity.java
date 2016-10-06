package com.example.administrator.travel.ui.me.changepassword;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.AvoidFastButton;
import com.example.administrator.travel.utils.MD5Utils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ShareUtil;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UserUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.xwray.passwordview.PasswordView;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by wangyang on 2016/10/2.
 */

public class ChangePassWordActivity extends LoadingBarBaseActivity<ChangePassWordEvent> {
    @ViewInject(R.id.pv_old_password)
    PasswordView mPvOldPassword;
    @ViewInject(R.id.pv_new_password)
    PasswordView mPvNewPassword;
    @ViewInject(R.id.pv_re_new_password)
    PasswordView mPvReNewPassword;
    @ViewInject(R.id.bt_next)
    AvoidFastButton mBtNext;
    private String newPassword;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initEvent() {
        mBtNext.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    /**
     * 修改密码
     */
    private void changePassword() {
        String oldPassword = getString(mPvOldPassword);
        newPassword = getString(mPvNewPassword);
        String reNewPassword = getString(mPvReNewPassword);
        if (StringUtils.isEmpty(oldPassword)) {
            ToastUtils.showToast("请输入旧密码");
            mPvOldPassword.requestFocus();
            return;
        }
        if (StringUtils.isEmpty(newPassword)) {
            ToastUtils.showToast("请输入新密码");
            mPvNewPassword.requestFocus();
            return;
        }
        if (StringUtils.isEmpty(reNewPassword)) {
            ToastUtils.showToast("请再次输入新密码");
            mPvReNewPassword.requestFocus();
            return;
        }
        if (!newPassword.equals(reNewPassword)) {
            ToastUtils.showToast("两次密码不一致");
            mPvReNewPassword.requestFocus();
            return;
        }
        if (newPassword.equals(oldPassword)){
            ToastUtils.showToast("新旧密码一致");
            mPvOldPassword.requestFocus();
            return;
        }
        Map<String, String> changePwdMap = MapUtils.Build().addKey(this).addOldPassWord(MD5Utils.encode(MD5Utils.encode(oldPassword))).addNewPassWord(MD5Utils.encode(MD5Utils.encode(newPassword))).addUserId().end();
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PASSWORD, changePwdMap, 0, new ChangePassWordEvent());
    }

    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "修改密码";
    }

    @Override
    protected void onSuccess(ChangePassWordEvent httpEvent) {
        ToastUtils.showToast(httpEvent.getMessage());
        if (StringUtils.isEmpty(newPassword)){
            finish();
            return;
        }
        UserInfo userInfo = UserUtils.getUserInfo();
        String encode = MD5Utils.encode(MD5Utils.encode(newPassword));
        userInfo.setPwd(encode);
        UserUtils.saveUserInfo(userInfo);
        ShareUtil.putString(this,IVariable.SAVE_PWD,encode);
        finish();
    }

    @Override
    protected void onFail(ChangePassWordEvent event) {

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
