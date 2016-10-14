package com.example.administrator.travel.ui.me.myappoint.chat;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.utils.MapUtils;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatActivity extends BaseNetWorkActivity {


    @Override
    protected void initEvent() {

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void onSuccess(HttpEvent event) {

    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected String initTitle() {
        return "聊天";
    }
}
