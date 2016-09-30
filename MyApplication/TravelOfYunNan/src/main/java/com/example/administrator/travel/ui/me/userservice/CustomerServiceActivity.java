package com.example.administrator.travel.ui.me.userservice;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;


/**
 * Created by Administrator on 2016/8/18 0018.
 * 客服中心
 */
public class CustomerServiceActivity extends LoadingBarBaseActivity<CustomerServiceEvent> {
    @ViewInject(R.id.et_phone) private EditText mEtPhone;
    @ViewInject(R.id.et_content) private EditText mEtContent;
    @ViewInject(R.id.bt_submit) private Button mBtSubmit;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initEvent() {
      mBtSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (StringUtils.isEmpty(getString(mEtPhone))){
                  ToastUtils.showToast("请输入您的手机号码，方便工作人员与你联系");
                  return;
              }
              if (StringUtils.isEmpty(getString(mEtContent))){
                  ToastUtils.showToast("请输入你需要反馈的内容！");
                  return;
              }
              sumbitMessage();

          }


      });
    }

    private void sumbitMessage() {
        Map<String, String> end = MapUtils.Build().addKey(this).addUserId().addContent(getString(mEtContent)).addTel(getString(mEtPhone)).addType("2").end();
        XEventUtils.postUseCommonBackJson(IVariable.USER_SERVICE_CENTER,end,0,new CustomerServiceEvent());
    }

    @Override
    protected void onLoad(int typeRefresh) {
        setIsProgress(false);
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected void onSuccess(CustomerServiceEvent customerService) {
        ToastUtils.showToast(customerService.getMessage());
    }

    @Override
    protected void onFail(HttpEvent event) {

    }

    @Override
    protected String setTitleName() {
        return "客服中心";
    }
}
