package com.example.administrator.travel.ui.me.messagecenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.example.administrator.travel.ui.baseui.MessagePrivateActivity;
import com.example.administrator.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.example.administrator.travel.ui.me.messagecenter.systemmessage.SystemMessageActivity;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BaseNetWorkActivity<MessageCenterEvent> implements View.OnClickListener {
    @BindView(R.id.ll_appoint_message) LinearLayout mLlAppointMessage;
    @BindView(R.id.ll_private) LinearLayout mLlPrivate;
    @BindView(R.id.ll_relate_me) LinearLayout mLlRelateMe;
    @BindView(R.id.ll_system_message) LinearLayout mLlSystemMessage;
    @BindView(R.id.bv_appoint_dot) BadgeView mBvAppointDot;
    @BindView(R.id.bv_private_dot) BadgeView mBvPrivateDot;
    @BindView(R.id.bv_system_dot) BadgeView mBvSystemDot;
    @BindView(R.id.bv_relate_me) BadgeView mBvRelateMe;










    @Override
    protected void initEvent() {
        mLlAppointMessage.setOnClickListener(this);
        mLlPrivate.setOnClickListener(this);
        mLlRelateMe.setOnClickListener(this);
        mLlSystemMessage.setOnClickListener(this);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
      builder.addType("2");
    }

    @Override
    protected String initUrl() {
        return IVariable.MESSAGE_CENTER_COUNT;
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
    protected void onSuccess(MessageCenterEvent messageCenterEvent) {
        MessageCenterBean object = GsonUtils.getObject(messageCenterEvent.getResult(), MessageCenterBean.class);
        MessageCenterBean.DataBean data = object.getData();
        int user = data.getUser();
        mBvRelateMe.setBadgeCount(user);
        mBvAppointDot.setBadgeCount(data.getTravel());
        mBvSystemDot.setBadgeCount(data.getSystem());
        mBvPrivateDot.setBadgeCount(data.getLetter());
    }



    @Override
    protected int initLayoutRes() {
        return R.layout.activity_message_center;
    }

    @Override
    protected String initTitle() {
        return "消息中心";
    }
}

