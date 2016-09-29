package com.example.administrator.travel.ui.me.messagecenter.appointmessage;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.MessageCommonEvent;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends LoadingBarBaseActivity<MessageCommonEvent>{
    @ViewInject(R.id.lv_appoint_message)
    private XListView mLvAppointMessage;
    private int count=0;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_message;
    }

    @Override
    protected void initEvent() {
        initXListView(mLvAppointMessage,true,true);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> appointMap = MapUtils.Build().addKey(this).addUserId().addPageSize().addCount(0).end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_APPOINT_MESSAGE,appointMap,type,new MessageCommonEvent());
    }

    @Override
    protected Activity initViewData() {

        return this;
    }

    @Override
    protected String setTitleName() {
        return "约伴消息";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(MessageCommonEvent messageCommonEvent) {

    }
}
