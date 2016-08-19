package com.example.administrator.travel.ui.activity;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 身份认证
 */
public class IdentityAuthenticationActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.tv_identity_car_card)
    private TextView mTvIdentityCarCard;
    @ViewInject(R.id.tv_identity_phone)
    private TextView mTvIdentityPhone;
    @ViewInject(R.id.tv_identity_id_card)
    private TextView mTvIdentityIdCard;
    @ViewInject(R.id.tv_identity_device_card)
    private TextView mTvIdentityDeviceCard;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected void initViewData() {
        setIsProgress(false);
        Login.UserInfo userInfo = GlobalUtils.getUserInfo();
        initIdentity(mTvIdentityPhone,userInfo.getTel());
        initIdentity(mTvIdentityIdCard,userInfo.getId_card());
        initIdentity(mTvIdentityCarCard,userInfo.getRun_card());
        initIdentity(mTvIdentityDeviceCard,userInfo.getDrive_card());

    }

    private void initIdentity(TextView tv,String text) {
        if (StringUtils.isEmpty(text) || text.equals("0")){
            tv.setText("未认证");
        }else {
            tv.setText("已认证");
            tv.setTextColor(getResources().getColor(R.color.otherTitleBg));
        }
    }

    @Override
    protected String setTitleName() {
        return "身份认证";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
