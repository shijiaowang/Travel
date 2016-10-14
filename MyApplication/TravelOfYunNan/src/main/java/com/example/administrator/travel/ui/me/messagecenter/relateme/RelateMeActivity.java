package com.example.administrator.travel.ui.me.messagecenter.relateme;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.RelateMeDetailActivity;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/26 0026.
 * 与我相关
 */
public class RelateMeActivity extends BaseNetWorkActivity<RelateMeEvent> implements View.OnClickListener {
    public static final int TYPE_AITE = 0;
    public static final int TYPE_DISCUSS = 1;
    public static final int TYPE_ZAMBIA = 2;//赞
    @BindView(R.id.rl_zambia) RelativeLayout mRlZambia;
    @BindView(R.id.rl_discuss) RelativeLayout mRlDiscuss;
    @BindView(R.id.rl_aite) RelativeLayout mRlAite;
    @BindView(R.id.bv_number_aite) BadgeView mBvNumberAite;
    @BindView(R.id.bv_number_discuss) BadgeView mBvNumberDiscuss;
    @BindView(R.id.bv_number_zan) BadgeView mBvNumberZan;




    @Override
    protected void initEvent() {
        mRlAite.setOnClickListener(this);
        mRlDiscuss.setOnClickListener(this);
        mRlZambia.setOnClickListener(this);
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
     builder.addType("3");
    }

    @Override
    protected String initUrl() {
        return IVariable.MESSAGE_CENTER_COUNT;
    }


    @Override
    protected void onSuccess(RelateMeEvent relateMeEvent) {
        RelateMeBean object = GsonUtils.getObject(relateMeEvent.getResult(), RelateMeBean.class);
        RelateMeBean.DataBean data = object.getData();
        mBvNumberAite.setBadgeCount(data.getInform());
        mBvNumberDiscuss.setBadgeCount(data.getReply());
        mBvNumberZan.setBadgeCount(data.getLike());
    }

    @Override
    protected void onFail(RelateMeEvent event) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RelateMeDetailActivity.class);
        switch (v.getId()) {
            case R.id.rl_aite:
                intent.putExtra(IVariable.TYPE, TYPE_AITE);
                startActivity(intent);
                break;
            case R.id.rl_discuss:
                intent.putExtra(IVariable.TYPE, TYPE_DISCUSS);
                startActivity(intent);
                break;
            case R.id.rl_zambia:
                intent.putExtra(IVariable.TYPE, TYPE_ZAMBIA);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_relate_me;
    }

    @Override
    protected String initTitle() {
        return "与我相关";
    }
}
