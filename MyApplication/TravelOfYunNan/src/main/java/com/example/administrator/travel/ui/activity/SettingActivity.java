package com.example.administrator.travel.ui.activity;


import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.ui.view.PhoneTextView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GlobalUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends LoadingBarBaseActivity{
    @ViewInject(R.id.tv_user_id)
    private TextView mTvUserId;
    @ViewInject(R.id.tv_user_nick_name)
    private TextView mTvUserNickName;
    @ViewInject(R.id.tv_user_live_place)
    private TextView mTvUserLivePlace;
    @ViewInject(R.id.tv_user_sex)
    private TextView mTvUserSex;
    @ViewInject(R.id.ptv_phone)
    private PhoneTextView mPtvPhone;



    @Override
    protected void initContentView() {
        TextView mTvCursorPro = FontsIconUtil.findIconFontsById(R.id.tv_cursor_pro,this);
        TextView mTvCursorPhone = FontsIconUtil.findIconFontsById(R.id.tv_cursor_phone,this);
        TextView mTvLogout = FontsIconUtil.findIconFontsById(R.id.tv_logout_icon,this);
        TextView mTvCamera = FontsIconUtil.findIconFontsById(R.id.tv_camera,this);
    }

    @Override
    protected int setContentLayout() {
        return  R.layout.activity_setting;
    }


    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }


    @Override
    protected void initViewData() {
        //// TODO: 2016/8/17 0017 如果userinfo为空，之后做处理
        Login.UserInfo userInfo = GlobalUtils.getUserInfo();
        try{
            mTvUserId.setText(userInfo.getId());
            mTvUserLivePlace.setText(userInfo.getProvince()+"-"+userInfo.getCity());
            mTvUserNickName.setText(userInfo.getNick_name());
            mTvUserSex.setText(userInfo.getSex().equals("1")?"男":"女");
            mPtvPhone.setPhoneNumber(userInfo.getTel());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        setIsProgress(false);

    }

    @Override
    protected String setTitleName() {
        return "设置";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
