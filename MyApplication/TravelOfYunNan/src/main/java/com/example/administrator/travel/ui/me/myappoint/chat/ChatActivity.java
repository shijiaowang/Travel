package com.example.administrator.travel.ui.me.myappoint.chat;

import android.view.MenuItem;

import com.example.administrator.travel.R;

import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatActivity extends BaseToolBarActivity{



    @Override
    protected int initLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initOptions() {

    }

    @Override
    protected String initRightText() {
        return "设置";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {

    }

    @Override
    protected String initTitle() {
        return "聊天";
    }
}
