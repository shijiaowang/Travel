package com.yunspeak.travel.ui.me.identityauth;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.identityauth.idauth.IdAuthActivity;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.StringUtils;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/19 0019.
 * 身份认证
 */
public class IdentityAuthenticationActivity extends BaseToolBarActivity implements View.OnClickListener {
    @BindView(R.id.tv_identity_car_card) TextView mTvIdentityCarCard;
    @BindView(R.id.tv_identity_phone) TextView mTvIdentityPhone;
    @BindView(R.id.tv_identity_id_card) TextView mTvIdentityIdCard;
    @BindView(R.id.tv_identity_device_card) TextView mTvIdentityDeviceCard;
    @BindView(R.id.rl_id_auth) RelativeLayout mRlIdAuth;
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
        switch (v.getId()){
            case R.id.rl_id_auth:
                startActivity(new Intent(this, IdAuthActivity.class));
                break;
        }
    }
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_identity_authentication;
    }
    @Override
    protected void initOptions() {
        setIsProgress(false);
        UserInfo userInfo = GlobalUtils.getUserInfo();
        if (userInfo != null) {
            String tel = userInfo.getTel();
            initIdentity(mTvIdentityPhone,tel);
            initClick(mRlIdAuth,tel);
            initIdentity(mTvIdentityIdCard, userInfo.getId_card());
            initIdentity(mTvIdentityCarCard, userInfo.getRun_card());
            initIdentity(mTvIdentityDeviceCard, userInfo.getDrive_card());
        }


    }
    private  void initClick(RelativeLayout relativeLayout,String string){
        try {
            if (string.equals("1"))return;
            relativeLayout.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected String initTitle() {
        return "身份认证";
    }
}
