package com.yunspeak.travel.ui.me.messagecenter.systemmessage;


import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseXListViewActivity;
import com.yunspeak.travel.ui.me.messagecenter.MeCommonEvent;
import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 系统消息
 */
public class SystemMessageActivity extends BaseXListViewActivity<MeCommonEvent,SystemMessageBean,SystemMessageBean.DataBean> {


    @Override
    protected String initUrl() {
        return IVariable.GET_SYSTEM_MESSAGE;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<SystemMessageBean.DataBean> httpData) {
        return new SystemMessageAdapter(this,httpData);
    }

    @Override
    protected String initTitle() {
        return "系统消息";
    }
}
