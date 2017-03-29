package com.yunspeak.travel.ui.me.userservice;


import android.content.Context;
import android.content.Intent;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.ServiceBinding;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseBarActivity;
import com.yunspeak.travel.ui.me.userservice.model.ServiceModel;


/**
 * Created by wangyang on 2017/3/29.
 * 客服中心 意见反馈
 */

public class ServiceActivity extends BaseBarActivity<ServiceBinding> {

    private boolean isService;

    @Override
    protected int initLayoutRes() {
        isService = getIntent().getBooleanExtra(IVariable.DATA,false);
        return R.layout.activity_service;
    }

    @Override
    protected void initOptions() {
        ServiceModel serviceModel = new ServiceModel();
        serviceModel.setService(isService);
        dataBinding.setServiceModel(serviceModel);
    }
    public static void start(Context context, boolean isService){
        Intent intent=new Intent(context,ServiceActivity.class);
        intent.putExtra(IVariable.DATA,isService);
        context.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return  isService?getString(R.string.server_center):getString(R.string.suggest_back);
    }
}
