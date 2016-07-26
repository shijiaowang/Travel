package com.example.administrator.travel.ui.activity;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 活动详情
 */
public class ActivateDetailActivity extends BarBaseActivity {
    @Override
    protected void initContentView() {
        TextView mTvPen = FontsIconUtil.findIconFontsById(R.id.tv_pen, this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_activate_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "活动详情";
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    public float getAlpha() {
        return 0f;
    }
}
