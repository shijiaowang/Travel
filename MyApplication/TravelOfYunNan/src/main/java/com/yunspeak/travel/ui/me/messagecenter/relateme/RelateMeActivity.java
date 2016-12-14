package com.yunspeak.travel.ui.me.messagecenter.relateme;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage.RelateMeDetailActivity;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/26 0026.
 * 与我相关
 */
public class RelateMeActivity extends BaseNetWorkActivity<RelateMeEvent> implements View.OnClickListener {

    @BindView(R.id.rl_zambia) RelativeLayout mRlZambia;
    @BindView(R.id.rl_discuss) RelativeLayout mRlDiscuss;
    @BindView(R.id.rl_aite) RelativeLayout mRlAite;
    @BindView(R.id.bv_number_aite) View mBvNumberAite;
    @BindView(R.id.bv_number_discuss) View mBvNumberDiscuss;
    @BindView(R.id.bv_number_zan) View mBvNumberZan;



    private void setShow(int i, View view) {
        if (i>0){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

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
        setShow(data.getInform(),mBvNumberAite);
        setShow(data.getReply(),mBvNumberDiscuss);
        setShow(data.getLike(),mBvNumberZan);
    }

    @Override
    protected void onFail(RelateMeEvent event) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RelateMeDetailActivity.class);
        switch (v.getId()) {
            case R.id.rl_aite:
                intent.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_AITE);
                startActivity(intent);
                mBvNumberAite.setVisibility(View.GONE);
                break;
            case R.id.rl_discuss:
                intent.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_DISCUSS);
                startActivity(intent);
                mBvNumberDiscuss.setVisibility(View.GONE);
                break;
            case R.id.rl_zambia:
                intent.putExtra(IVariable.TYPE, AppointMessageAdapter.TYPE_ZAMBIA);
                startActivity(intent);
                mBvNumberZan.setVisibility(View.GONE);
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
