package com.yunspeak.travel.ui.me.setting;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCropPhotoActivity;
import com.yunspeak.travel.ui.me.changephone.ChangePhoneActivity;
import com.yunspeak.travel.ui.baseui.HomeActivity;
import com.yunspeak.travel.ui.baseui.PersonalProfileActivity;
import com.yunspeak.travel.ui.home.welcome.splash.SplashActivity;
import com.yunspeak.travel.ui.me.about.AboutActivity;
import com.yunspeak.travel.ui.me.changepassword.ChangePassWordActivity;
import com.yunspeak.travel.ui.view.PhoneTextView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.hyphenate.chat.EMClient;
import com.yalantis.ucrop.UCrop;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by wangyang on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends BaseCropPhotoActivity<SettingEvent> implements View.OnClickListener {
    @ViewInject(R.id.tv_user_id)
    private TextView mTvUserId;
    @ViewInject(R.id.tv_user_nick_name)
    private TextView mTvUserNickName;
    @ViewInject(R.id.iv_icon)
    private SimpleDraweeView mIvIcon;
    @ViewInject(R.id.tv_change_icon)
    private TextView mTvChangeIcon;
    @ViewInject(R.id.tv_user_live_place)
    private TextView mTvUserLivePlace;
    @ViewInject(R.id.tv_user_sex)
    private TextView mTvUserSex;
    @ViewInject(R.id.ll_phone)
    private LinearLayout mLlPhone;//更改手机
    @ViewInject(R.id.ll_profile)
    private LinearLayout mLlProfile;//个人简介
    @ViewInject(R.id.ptv_phone)
    private PhoneTextView mPtvPhone;
    @ViewInject(R.id.ll_logout)
    private LinearLayout mLlLogout;
    @ViewInject(R.id.ll_about)
    private LinearLayout mLlAbout;
    @ViewInject(R.id.ll_change_password)
    private LinearLayout mLlChangePassword;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initChildListener() {
        mLlPhone.setOnClickListener(this);
        mLlProfile.setOnClickListener(this);
        mLlLogout.setOnClickListener(this);
        mLlAbout.setOnClickListener(this);
        mLlChangePassword.setOnClickListener(this);
        mTvChangeIcon.setOnClickListener(this);
    }

    @Override
    protected SimpleDraweeView childViewShow() {
        return mIvIcon;
    }

    @Override
    protected void onLoad(int typeRefresh) {
         setIsProgress(false);
    }


    @Override
    protected Activity initViewData() {
        //// TODO: 2016/8/17 0017 如果userinfo为空，之后做处理
        UserInfo userInfo = GlobalUtils.getUserInfo();
        try {
            mTvUserId.setText(userInfo.getId());
            mTvUserLivePlace.setText(userInfo.getProvince() + "-" + userInfo.getCity());
            mTvUserNickName.setText(userInfo.getNick_name());
            mTvUserSex.setText(userInfo.getSex().equals("1") ? "男" : "女");
            mPtvPhone.setPhoneNumber(userInfo.getTel());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setIsProgress(false);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "设置";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

    @Override
    protected void onSuccess(SettingEvent settingEvent) {

    }
    @Override
    protected void onFail(SettingEvent settingEvent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_phone:
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.ll_profile:
                startActivity(new Intent(this, PersonalProfileActivity.class));
                break;
            case R.id.ll_logout:
                EMClient.getInstance().logout(true);
                ShareUtil.putString(this, IVariable.SAVE_NAME, "");
                ShareUtil.putString(this, IVariable.SAVE_PWD, "");
                startActivity(new Intent(this, SplashActivity.class));
                setResult(HomeActivity.RESULT);
                finish();
                break;
            case R.id.ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.ll_change_password:
                startActivity(new Intent(this, ChangePassWordActivity.class));
                break;
            case R.id.tv_change_icon:
                showPictureCutPop(mLlLogout);
                break;
        }
    }

    @Override
    protected void setOptions(UCrop.Options options) {
        options.setCircleDimmedLayer(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewData();
    }
}
