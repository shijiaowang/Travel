package com.yunspeak.travel.ui.me.identityauth;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.changephone.ChangePhoneActivity;
import com.yunspeak.travel.ui.me.identityauth.idauth.DriverAuthActivity;
import com.yunspeak.travel.ui.me.identityauth.idauth.IdAuthActivity;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/19 0019.
 * 身份认证
 */
public class IdentityAuthenticationActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.tv_identity_car_card)
    TextView mTvIdentityCarCard;
    @BindView(R.id.tv_identity_phone)
    TextView mTvIdentityPhone;
    @BindView(R.id.tv_identity_id_card)
    TextView mTvIdentityIdCard;
    @BindView(R.id.tv_identity_device_card)
    TextView mTvIdentityDeviceCard;
    @BindView(R.id.rl_id_auth) RelativeLayout mRlIdAuth;
    @BindView(R.id.rl_change_phone) RelativeLayout mRlChangePhone;
    @BindView(R.id.rl_dirver) RelativeLayout rlDirver;
    @BindView(R.id.rl_car) RelativeLayout rlCar;

    private void initIdentity(TextView tv, String text) {
        if (StringUtils.isEmpty(text) || text.equals("0")) {
            tv.setText("未认证");
        } else {
            tv.setText("已认证");
            tv.setTextColor(getResources().getColor(R.color.otherTitleBg));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_id_auth:
               IdAuthActivity.start(this,true);
                break;
            case R.id.rl_car:
                IdAuthActivity.start(this,false);
                break;
            case R.id.rl_change_phone:
                startActivity(new Intent(IdentityAuthenticationActivity.this, ChangePhoneActivity.class));
                break;
            case R.id.rl_dirver:
                startActivity(new Intent(this, DriverAuthActivity.class));
                break;
        }
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    public void onResume() {
        super.onResume();
        initOptions();
    }

    @Override
    protected void initOptions() {
        setIsProgress(false);
        UserInfo userInfo = GlobalUtils.getUserInfo();
        mRlChangePhone.setOnClickListener(this);
        if (userInfo != null) {
            String tel = userInfo.getTel();
            initIdentity(mTvIdentityPhone, tel);
            initClick(mRlIdAuth, userInfo.getId_card());
            initClick(rlCar, userInfo.getRun_card());
            initClick(rlDirver, userInfo.getDrive_card());
            initIdentity(mTvIdentityIdCard, userInfo.getId_card());
            initIdentity(mTvIdentityCarCard, userInfo.getRun_card());
            initIdentity(mTvIdentityDeviceCard, userInfo.getDrive_card());
        }else {
            ToastUtils.showToast("数据错误");
        }


    }

    private void initClick(RelativeLayout relativeLayout, String string) {
        relativeLayout.setOnClickListener(new ShowWhatOnClickListener(string));
    }

    @Override
    protected String initTitle() {
        return "身份认证";
    }

    /**
     * 展示多种可能点击事件
     */
    class ShowWhatOnClickListener implements View.OnClickListener {
        private String bindText;

        public ShowWhatOnClickListener(String bindText) {
            this.bindText = bindText;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(bindText) || bindText.equals("0")){
                IdentityAuthenticationActivity.this.onClick(v);
            }else {
                ToastUtils.showToast("您已经认证过啦，无需再次认证。");
            }
        }
    }

}
