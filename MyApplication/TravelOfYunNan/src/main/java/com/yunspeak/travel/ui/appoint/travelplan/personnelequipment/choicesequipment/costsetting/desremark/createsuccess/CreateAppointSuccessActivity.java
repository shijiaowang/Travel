package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess;

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

/**
 * Created by wangyang on 2016/9/1 0001.
 * 发布约伴成功
 */
public class CreateAppointSuccessActivity extends BaseNetWorkActivity<CreateAppointSuccessEvent> {
    @BindView(R.id.ll_key) LinearLayout mLlKey;
    @BindView(R.id.tv_code) TextView tvCode;
    @BindView(R.id.tv_share) AvoidFastButton tvShare;
    @BindView(R.id.tv_appoint) AvoidFastButton tvAppoint;
    @BindView(R.id.ll_root) LinearLayout mLlRoot;
    private String id;
   @Override
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
                MyAppointActivity.start(CreateAppointSuccessActivity.this,MyAppointActivity.PASSED);
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
    protected void onSuccess(CreateAppointSuccessEvent createSuccessEvent) {
        CreateSuccessBean createSuccessBean = GsonUtils.getObject(createSuccessEvent.getResult(), CreateSuccessBean.class);
        final CreateSuccessBean.DataBean data = createSuccessBean.getData();
        tvCode.setText(data.getId_code());
        tvShare.setOnAvoidFastOnClickListener(new AvoidFastButton.AvoidFastOnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showShareDialog(CreateAppointSuccessActivity.this, data.getTitle(), data.getContent(), data.getShare_url(), mLlRoot);
            }
        });
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_appoint_success;
    }


    @Override
    protected String initTitle() {
        return "发布成功";
    }


}
