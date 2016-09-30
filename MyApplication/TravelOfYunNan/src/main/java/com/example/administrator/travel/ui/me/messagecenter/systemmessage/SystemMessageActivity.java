package com.example.administrator.travel.ui.me.messagecenter.systemmessage;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.MeCommonEvent;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 系统消息
 */
public class SystemMessageActivity extends LoadAndRefreshBaseActivity<MeCommonEvent,SystemMessageBean,SystemMessageBean.DataBean> {
    @ViewInject(R.id.lv_system_message)
    private XListView mLvSystemMessage;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_system_message;
    }
    @Override
    protected void initEvent() {
        initXListView(mLvSystemMessage,true,true);
    }
    @Override
    public XListView setXListView() {
        return mLvSystemMessage;
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_SYSTEM_MESSAGE;
    }


    @Override
    protected Activity initViewData() {
        return this;
    }
    @Override
    protected String setTitleName() {
        return "系统消息";
    }
    @Override
    public float getAlpha() {
        return 1.0f;
    }
    @Override
    protected TravelBaseAdapter initAdapter(List<SystemMessageBean.DataBean> httpData) {
        return new SystemMessageAdapter(this,httpData);
    }
}
