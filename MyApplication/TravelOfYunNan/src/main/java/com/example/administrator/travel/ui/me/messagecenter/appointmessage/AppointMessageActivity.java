package com.example.administrator.travel.ui.me.messagecenter.appointmessage;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.MeCommonEvent;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends LoadAndRefreshBaseActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    @ViewInject(R.id.lv_appoint_message)
    private XListView mLvAppointMessage;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_message;
    }

    @Override
    protected void initEvent() {
        initXListView(mLvAppointMessage,true,true);
    }

    @Override
    public XListView setXListView() {
        return mLvAppointMessage;
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_APPOINT_MESSAGE;
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
    protected TravelBaseAdapter initAdapter(List<CommonMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(this,httpData);
    }


}
