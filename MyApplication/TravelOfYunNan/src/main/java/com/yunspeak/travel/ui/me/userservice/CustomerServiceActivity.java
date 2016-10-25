package com.yunspeak.travel.ui.me.userservice;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/8/18 0018.
 * 客服中心
 */
public class CustomerServiceActivity extends BaseNetWorkActivity<CustomerServiceEvent> {
    @BindView(R.id.et_phone)  EditText mEtPhone;
    @BindView(R.id.et_content)EditText mEtContent;
    @BindView(R.id.bt_submit) Button mBtSubmit;


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
              onLoad(TYPE_UPDATE);

          }


      });
    }



    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addContent(getString(mEtContent)).addTel(getString(mEtPhone)).addType("2");
    }

    @Override
    protected String initUrl() {
        return IVariable.USER_SERVICE_CENTER;
    }



    @Override
    protected void onSuccess(CustomerServiceEvent customerService) {
        mEtContent.setText("");
        mEtPhone.setText("");
        ToastUtils.showToast(customerService.getMessage());
    }

    @Override
    protected void onFail(CustomerServiceEvent event) {

    }


    @Override
    protected int initLayoutRes() {
        return  R.layout.activity_customer_service;
    }

    @Override
    protected String initTitle() {
        return "客服中心";
    }
}
