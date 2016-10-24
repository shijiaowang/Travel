package com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage;

import android.app.Activity;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseXListViewActivity;
import com.yunspeak.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.yunspeak.travel.ui.me.messagecenter.MeCommonEvent;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.yunspeak.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 *  与我相关消息
 */
public class RelateMeDetailActivity extends BaseXListViewActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    private int type;
    private String url;
    private String title;

    @Override
    protected void initEvent() {
        super.initEvent();
        type = getIntent().getIntExtra(IVariable.TYPE, -1);
        title = "与我相关";
        switch (type){
            case RelateMeActivity.TYPE_AITE:
                title ="@我的";
                url=IVariable.AITE_ME_MESSAGE;
                break;
            case RelateMeActivity.TYPE_DISCUSS:
                title ="评论我的";
                url=IVariable.REPLY_ME_MESSAGE;
                break;
            case RelateMeActivity.TYPE_ZAMBIA:
                title ="赞我的";
                url=IVariable.ZAN_ME_MESSAGE;
                break;
        }
         getmTvTitle().setText(title);
    }




    @Override
    protected String initUrl() {
        return url;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<CommonMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(this,httpData);
    }




    @Override
    protected String initTitle() {
        return "@我";
    }
}
