package com.yunspeak.travel.ui.me.userservice.model;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.view.View;

import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.ICallBack;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.PhoneUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/29.
 */

public class ServiceModel {
    private boolean isService;
    private String content;
    private String phone;

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void onSubmit(View view){
        if (StringUtils.isEmpty(content)){
            ToastUtils.showToast("请输入你需要反馈的内容！");
            return;
        }
        if (PhoneUtils.checkPhoneNumber(phone)){
            return;
        }
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addContent(content).addTel(phone).addType(isService ? "2" : "1").end();
        HttpClient.getInstance().postDataNoBackMessage(IRequestUrl.USER_SERVICE_CENTER, end,view.getContext(),true);
    }
}
