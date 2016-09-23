package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.SystemMessageAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/26 0026.
 * 系统消息
 */
public class SystemMessageActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_system_message)
    private ListView mLvSystemMessage;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        mLvSystemMessage.setAdapter(new SystemMessageAdapter(this,null));
        return null;
    }

    @Override
    protected String setTitleName() {
        return "系统消息";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
