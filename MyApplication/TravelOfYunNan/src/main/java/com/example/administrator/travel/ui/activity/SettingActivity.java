package com.example.administrator.travel.ui.activity;


import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends BarBaseActivity{




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
    protected void initData() {

    }

    @Override
    protected void initViewData() {

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
