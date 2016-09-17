package com.example.administrator.travel.ui.me;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;

/**
 * Created by android on 2016/9/17.
 * 等级
 */
public class LevelActivity extends LoadingBarBaseActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_level;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return null;
    }

    @Override
    protected String setTitleName() {
        return "等级";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
