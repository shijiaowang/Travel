package com.yunspeak.travel.ui.me.messagecenter.appointmessage;


import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BaseXListViewActivity;
import com.yunspeak.travel.ui.me.messagecenter.MeCommonEvent;
import com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;
import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends BaseRecycleViewActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    @Override
    protected String initUrl() {
        return IVariable.GET_APPOINT_MESSAGE;
    }

    @Override
    protected BaseRecycleViewAdapter<CommonMessageBean.DataBean> initAdapter(List<CommonMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(httpData,this);
    }
    @Override
    protected String initTitle() {
        return "约伴消息";
    }
}
