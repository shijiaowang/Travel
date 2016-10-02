package com.example.administrator.travel.ui.me.changepassword;

import android.app.Activity;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.xwray.passwordview.PasswordView;

import org.xutils.view.annotation.ViewInject;

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
    PasswordView mBtNext;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initEvent() {
        mPvReNewPassword.setOnClickListener(new View.OnClickListener() {
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
        String newPassword = getString(mPvNewPassword);
        String reNewPassword = getString(mPvReNewPassword);
        if (StringUtils.isEmpty(oldPassword)){
            ToastUtils.showToast("请输入旧密码");
            mPvOldPassword.requestFocus();
            return;
        }
        if (StringUtils.isEmpty(newPassword)){
            ToastUtils.showToast("请输入新密码");
            mPvNewPassword.requestFocus();
            return;
        }
        if (StringUtils.isEmpty(reNewPassword)){
            ToastUtils.showToast("请再次输入新密码");
            mPvReNewPassword.requestFocus();
            return;
        }


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
    }

    @Override
    protected void onFail(ChangePassWordEvent event) {

    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
