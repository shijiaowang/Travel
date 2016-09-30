package com.example.administrator.travel.ui.me.messagecenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.MessagePrivateActivity;
import com.example.administrator.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.example.administrator.travel.ui.me.messagecenter.systemmessage.SystemMessageActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import su.levenetc.android.badgeview.BadgeView;

/**
 * Created by wangyang on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends LoadingBarBaseActivity<MessageCenterEvent> implements View.OnClickListener {
    private TextView mTvMore;
    @ViewInject(R.id.ll_appoint_message)
    private LinearLayout mLlAppointMessage;
    @ViewInject(R.id.ll_private)
    private LinearLayout mLlPrivate;
    @ViewInject(R.id.ll_relate_me)
    private LinearLayout mLlRelateMe;
    @ViewInject(R.id.ll_system_message)
    private LinearLayout mLlSystemMessage;
    @ViewInject(R.id.bv_appoint_dot) BadgeView mBvAppointDot;
    @ViewInject(R.id.bv_private_dot) BadgeView mBvPrivateDot;
    @ViewInject(R.id.bv_system_dot) BadgeView mBvSystemDot;
    @ViewInject(R.id.bv_relate_me) BadgeView mBvRelateMe;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_message_center;
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "消息中心";
    }


    @Override
    protected void initEvent() {
        init();
        mLlAppointMessage.setOnClickListener(this);
        mLlPrivate.setOnClickListener(this);
        mLlRelateMe.setOnClickListener(this);
        mLlSystemMessage.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> end = MapUtils.Build().addKey(this).addUserId().addType("2").end();
        XEventUtils.getUseCommonBackJson(IVariable.MESSAGE_CENTER_COUNT,end,0,new MessageCenterEvent());
    }

    private void init() {
        mTvMore = getmTvRightIcon();
        mTvMore.setTypeface(TypefaceUtis.getTypeface(this));
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_system_message:
                startActivity(new Intent(this, SystemMessageActivity.class));
                break;
            case R.id.ll_appoint_message:
                startActivity(new Intent(this, AppointMessageActivity.class));
                break;
            case R.id.ll_private:
                startActivity(new Intent(this, MessagePrivateActivity.class));
                break;
            case R.id.ll_relate_me:
                startActivity(new Intent(this, RelateMeActivity.class));
                break;
        }
    }


    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(MessageCenterEvent messageCenterEvent) {
        MessageCenterBean object = GsonUtils.getObject(messageCenterEvent.getResult(), MessageCenterBean.class);
        MessageCenterBean.DataBean data = object.getData();
        int user = data.getUser();
        mBvRelateMe.setValue(user);
        mBvAppointDot.setValue(data.getTravel());
        mBvSystemDot.setValue(data.getSystem());
        mBvPrivateDot.setValue(data.getLetter());
    }

    @Override
    protected void onFail(HttpEvent event) {

    }
}

