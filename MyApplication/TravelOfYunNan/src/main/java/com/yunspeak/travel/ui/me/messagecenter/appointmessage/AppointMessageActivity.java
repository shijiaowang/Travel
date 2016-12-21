package com.yunspeak.travel.ui.me.messagecenter.appointmessage;


import com.yunspeak.travel.bean.AppointMessageBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost.ReplyEvent;
import com.yunspeak.travel.ui.me.messagecenter.MeCommonEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 约伴消息
 */
public class AppointMessageActivity extends BaseRecycleViewActivity<MeCommonEvent,AppointMessageBean,AppointMessageBean.DataBean> {
    @Override
    protected String initUrl() {
        return IVariable.GET_APPOINT_MESSAGE;
    }


    @Override
    protected BaseRecycleViewAdapter<AppointMessageBean.DataBean> initAdapter(List<AppointMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(httpData,this,AppointMessageAdapter.TYPE_APPOINT);
    }
    @Override
    protected String initTitle() {
        return "出行消息";
    }
    @Subscribe
    public void onEvent(ReplyEvent replyEvent){
        onLoad(TYPE_REFRESH);
    }
}
