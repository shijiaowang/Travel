package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.ui.view.AvoidFastButton;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import butterknife.BindView;

import static anetwork.channel.http.NetworkSdkSetting.context;

/**
 * Created by wangyang on 2016/9/1 0001.
 * 发布约伴成功
 */
public class CreateAppointSuccessActivity extends BaseNetWorkActivity<CreateSuccessEvent> {
    @BindView(R.id.ll_key) LinearLayout mLlKey;
    @BindView(R.id.tv_code) TextView tvCode;
    @BindView(R.id.tv_share) AvoidFastButton tvShare;
    @BindView(R.id.tv_appoint) AvoidFastButton tvAppoint;
    private String id;

    protected void initEvent() {
        if (GlobalValue.mAppointType == IVariable.TYPE_WITH_ME) {
            mLlKey.setVisibility(View.GONE);
            tvShare.setVisibility(View.GONE);
        }else {
            id = getIntent().getStringExtra(IVariable.ID);

        }
        tvAppoint.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAppointSuccessActivity.this,MyAppointActivity.class));
                finish();
            }
        });
    }

    @Override
    protected boolean isAutoLoad() {
        return GlobalValue.mAppointType==IVariable.TYPE_TOGETHER;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
             builder.addId(id);
    }

    @Override
    protected String initUrl() {
        return IVariable.SHARE_SUCCESS;
    }

    @Override
    protected void onSuccess(CreateSuccessEvent createSuccessEvent) {
        CreateSuccessBean createSuccessBean = GsonUtils.getObject(createSuccessEvent.getResult(), CreateSuccessBean.class);
        final CreateSuccessBean.DataBean data = createSuccessBean.getData();
        tvCode.setText(data.getId_code());
        tvShare.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showShareDialog(context, data.getTitle(), data.getContent(), data.getShare_url(), null);
            }
        });
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_appoint_success;
    }

    @Override
    protected void initOptions() {
        initEvent();
    }

    @Override
    protected String initTitle() {
        return "发布成功";
    }


}
