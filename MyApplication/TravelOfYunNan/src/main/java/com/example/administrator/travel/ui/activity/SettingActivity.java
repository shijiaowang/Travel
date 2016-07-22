package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends BaseActivity{

    private TextView mTvBack;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        TextView mTvCursorPro = FontsIconUtil.findIconFontsById(R.id.tv_cursor_pro,this);
        TextView mTvCursorPhone = FontsIconUtil.findIconFontsById(R.id.tv_cursor_phone,this);
        TextView mTvLogout = FontsIconUtil.findIconFontsById(R.id.tv_logout_icon,this);
        TextView mTvCamera = FontsIconUtil.findIconFontsById(R.id.tv_camera,this);
        mTvBack = FontsIconUtil.findIconFontsById(R.id.tv_back, this);
    }

    @Override
    protected void initListener() {
       mTvBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    @Override
    protected void initData() {

    }
}
