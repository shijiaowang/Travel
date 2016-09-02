package com.example.administrator.travel.ui.activity;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.AppointTogetherDetailEvent;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 一起玩约伴详情
 */
public class AppointTogetherDetailActivity extends LoadingBarBaseActivity {
    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_together_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "约伴详情";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    @Subscribe
    public void onEvent(AppointTogetherDetailEvent event){

    }
}
