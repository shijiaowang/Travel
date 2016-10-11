package com.example.administrator.travel.ui.me.messagecenter.appointmessage;


import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.BaseXListViewActivity;
import com.example.administrator.travel.ui.me.messagecenter.MeCommonEvent;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;
import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends BaseXListViewActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    @Override
    protected String initUrl() {
        return IVariable.GET_APPOINT_MESSAGE;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<CommonMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(this,httpData);
    }
    @Override
    protected String initTitle() {
        return "约伴消息";
    }
}
