package com.yunspeak.travel.ui.me.setting;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.me.changephone.ChangePhoneActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.baseui.PersonalProfileActivity;
import com.yunspeak.travel.ui.home.welcome.splash.SplashActivity;
import com.yunspeak.travel.ui.me.about.AboutActivity;
import com.yunspeak.travel.ui.me.changepassword.ChangePassWordActivity;
import com.yunspeak.travel.ui.view.PhoneTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.hyphenate.chat.EMClient;
import com.yalantis.ucrop.UCrop;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends BaseCutPhotoActivity<SettingEvent> implements View.OnClickListener {
    @BindView(R.id.tv_user_id) TextView mTvUserId;
    @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_change_icon) TextView mTvChangeIcon;
    @BindView(R.id.tv_user_live_place) TextView mTvUserLivePlace;
    @BindView(R.id.tv_user_sex) TextView mTvUserSex;
    @BindView(R.id.ll_phone) LinearLayout mLlPhone;//更改手机
    @BindView(R.id.ll_profile) LinearLayout mLlProfile;//个人简介
    @BindView(R.id.ptv_phone) PhoneTextView mPtvPhone;
    @BindView(R.id.ll_logout) LinearLayout mLlLogout;
    @BindView(R.id.ll_about) LinearLayout mLlAbout;
    @BindView(R.id.ll_change_password) LinearLayout mLlChangePassword;
    private UserInfo userInfo;

    @Override
    protected SimpleDraweeView childViewShow() {
        return mIvIcon;
    }

    @Override
    protected void initEvent() {
        mLlPhone.setOnClickListener(this);
        mLlProfile.setOnClickListener(this);
        mLlLogout.setOnClickListener(this);
        mLlAbout.setOnClickListener(this);
        mLlChangePassword.setOnClickListener(this);
        mTvChangeIcon.setOnClickListener(this);
        initData();

    }

    private void initData() {
        userInfo = GlobalUtils.getUserInfo();
        try {
            mTvUserId.setText(userInfo.getId());
            FrescoUtils.displayIcon(mIvIcon, userInfo.getUser_img());
            mTvUserLivePlace.setText(userInfo.getProvince() + "-" + userInfo.getCity());
            mTvUserNickName.setText(userInfo.getNick_name());
            mTvUserSex.setText(userInfo.getSex().equals("1") ? "男" : "女");
            mPtvPhone.setPhoneNumber(userInfo.getTel());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setIsProgress(false);
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
                PushAgent.getInstance(this).removeAlias(userInfo.getId(), "YUNS_ID", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        LogUtils.e("是否成功"+b+"信息"+s);
                    }
                });
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
    protected int initLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected String initTitle() {
        return "设置";
    }
}
