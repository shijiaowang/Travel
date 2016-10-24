package com.yunspeak.travel.ui.baseui;

import android.widget.ListView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.MessagePrivateAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 私信
 */
public class MessagePrivateActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_private)
    private ListView mLvPrivate;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_message_privates;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mLvPrivate.setAdapter(new MessagePrivateAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "私信";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
