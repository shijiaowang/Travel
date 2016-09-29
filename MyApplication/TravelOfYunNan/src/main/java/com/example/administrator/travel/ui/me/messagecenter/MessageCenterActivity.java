package com.example.administrator.travel.ui.me.messagecenter;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.MessagePrivateActivity;
import com.example.administrator.travel.ui.baseui.RelateMeActivity;
import com.example.administrator.travel.ui.baseui.SystemMessageActivity;
import com.example.administrator.travel.utils.TypefaceUtis;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by wangyang on 2016/7/15 0015.
 * 消息中心
 */
public class MessageCenterActivity extends BarBaseActivity implements View.OnClickListener {
    private TextView mTvMore;
    @ViewInject(R.id.ll_appoint_message)
    private LinearLayout mLlAppointMessage;
    @ViewInject(R.id.ll_private)
    private LinearLayout mLlPrivate;
    @ViewInject(R.id.ll_relate_me)
    private LinearLayout mLlRelateMe;
    @ViewInject(R.id.ll_system_message)
    private LinearLayout mLlSystemMessage;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_message_center;
    }


    @Override
    protected void initViewData() {

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

    private void init() {
        mTvMore = getmTvRightIcon();
        mTvMore.setTypeface(TypefaceUtis.getTypeface(this));
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_system_message:
                startActivity(new Intent(this,SystemMessageActivity.class));
                break;
            case R.id.ll_appoint_message:
                startActivity(new Intent(this,AppointMessageActivity.class));
                break;
            case R.id.ll_private:
                startActivity(new Intent(this,MessagePrivateActivity.class));
                break;
            case R.id.ll_relate_me:
                startActivity(new Intent(this,RelateMeActivity.class));
                break;
        }
    }



    @Override
    public float getAlpha() {
        return 1.0f;
    }

}

