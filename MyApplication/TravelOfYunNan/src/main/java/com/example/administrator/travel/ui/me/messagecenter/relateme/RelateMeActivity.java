package com.example.administrator.travel.ui.me.messagecenter.relateme;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Active;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.MessageCenterEvent;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.RelateMeDetailActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import su.levenetc.android.badgeview.BadgeView;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 与我相关
 */
public class RelateMeActivity extends LoadingBarBaseActivity<RelateMeEvent> implements View.OnClickListener {
    public static final int TYPE_AITE = 0;
    public static final int TYPE_DISCUSS = 1;
    public static final int TYPE_ZAMBIA = 2;//赞
    @ViewInject(R.id.rl_zambia)
    private RelativeLayout mRlZambia;
    @ViewInject(R.id.rl_discuss)
    private RelativeLayout mRlDiscuss;
    @ViewInject(R.id.rl_aite)
    private RelativeLayout mRlAite;
    @ViewInject(R.id.bv_number_aite) BadgeView mBvNumberAite;
    @ViewInject(R.id.bv_number_discuss) BadgeView mBvNumberDiscuss;
    @ViewInject(R.id.bv_number_zan) BadgeView mBvNumberZan;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_relate_me;
    }

    @Override
    protected void initEvent() {
        mRlAite.setOnClickListener(this);
        mRlDiscuss.setOnClickListener(this);
        mRlZambia.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> end = MapUtils.Build().addKey(this).addUserId().addType("3").end();
        XEventUtils.getUseCommonBackJson(IVariable.MESSAGE_CENTER_COUNT,end,0,new RelateMeEvent());
    }

    @Override
    protected Activity initViewData() {
      return this;
    }

    @Override
    protected String setTitleName() {
        return "与我相关";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(RelateMeEvent relateMeEvent) {
        RelateMeBean object = GsonUtils.getObject(relateMeEvent.getResult(), RelateMeBean.class);
        RelateMeBean.DataBean data = object.getData();
        mBvNumberAite.setValue(data.getInform());
        mBvNumberDiscuss.setValue(data.getReply());
        mBvNumberZan.setValue(data.getLike());
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
}
