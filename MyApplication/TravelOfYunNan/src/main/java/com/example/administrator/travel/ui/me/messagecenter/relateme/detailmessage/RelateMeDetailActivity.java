package com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.MeCommonEvent;
import com.example.administrator.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.example.administrator.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 *  与我相关消息
 */
public class RelateMeDetailActivity extends LoadAndRefreshBaseActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    @ViewInject(R.id.lv_relate_me)
    private XListView mLvRelateMe;
    private int type;
    private Class aClass;
    private String url;
    private String title;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_relate_me_detail;
    }

    @Override
    protected void initEvent() {
        initXListView(mLvRelateMe,true,true);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        changeTitle(title);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }


    @Override
    public XListView setXListView() {
        return mLvRelateMe;
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
    protected String setTitleName() {
        return "@我";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }


}
