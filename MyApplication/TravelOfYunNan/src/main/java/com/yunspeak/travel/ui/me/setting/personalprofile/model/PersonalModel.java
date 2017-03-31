package com.yunspeak.travel.ui.me.setting.personalprofile.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.ICallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.BR;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/29.
 */

public class PersonalModel extends BaseObservable{
    private String preDes;
    private  String des;

    public PersonalModel(String des){
        this.des=des;
        this.preDes=des;
    }
    @Bindable
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
        notifyPropertyChanged(BR.des);
    }
    public void onChange(View view){
        if (StringUtils.isEqual(des,preDes)){
            ToastUtils.showToast(UIUtils.getString(R.string.no_change));
            return;
        }
        Map<String, String> contentMap = MapUtils.Build().addKey().add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()+"").addContent(des).end();
        HttpClient.getInstance().postDataOneBack(IRequestUrl.CHANGE_USER_INFO, contentMap, new ICallBack() {
            @Override
            public void back(boolean isSuccess, String message) {
                if (isSuccess){
                    preDes=des;
                    GlobalUtils.getUserInfo().setContent(des);
                    //// TODO: 2017/3/29 可做数据库存储，每次登录进入页面就已经更新数据到数据库了，这里不是必须， 
                }
                ToastUtils.showToast(message);
            }
        }, view.getContext(),true);

    }
}
