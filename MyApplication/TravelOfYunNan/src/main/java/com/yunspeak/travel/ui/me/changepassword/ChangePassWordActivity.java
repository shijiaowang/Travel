package com.yunspeak.travel.ui.me.changepassword;

import android.view.View;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.ui.view.LoginEditText;
import com.yunspeak.travel.utils.MD5Utils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.xwray.passwordview.PasswordView;
import java.util.Map;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/2.
 */

public class ChangePassWordActivity extends BaseNetWorkActivity<ChangePassWordEvent> {
    @BindView(R.id.pv_old_password) LoginEditText mPvOldPassword;
    @BindView(R.id.pv_new_password) LoginEditText mPvNewPassword;
    @BindView(R.id.pv_re_new_password) LoginEditText mPvReNewPassword;
    @BindView(R.id.bt_next) AvoidFastButton mBtNext;
    private String newPassword;


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
        String oldPassword = mPvOldPassword.getString();
        newPassword = mPvNewPassword.getString();
        String reNewPassword = mPvReNewPassword.getString();
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
        Map<String, String> changePwdMap = MapUtils.Build().addKey().addOldPassWord(MD5Utils.encode(MD5Utils.encode(oldPassword))).addNewPassWord(MD5Utils.encode(MD5Utils.encode(newPassword))).addUserId().end();
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_PASSWORD, changePwdMap, 0, new ChangePassWordEvent());
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
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
    protected int initLayoutRes() {
        return R.layout.activity_change_password;
    }

    @Override
    protected String initTitle() {
        return "修改密码";
    }
}
