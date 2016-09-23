package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.AppointMessageAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_appoint_message)
    private ListView mLvAppointMessage;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_message;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        mLvAppointMessage.setAdapter(new AppointMessageAdapter(this,null));
        return null;
    }

    @Override
    protected String setTitleName() {
        return "约伴消息";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
