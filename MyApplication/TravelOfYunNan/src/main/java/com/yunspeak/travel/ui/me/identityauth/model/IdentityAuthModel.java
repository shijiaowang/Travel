package com.yunspeak.travel.ui.me.identityauth.model;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.ui.me.changephone.ChangePhoneActivity;
import com.yunspeak.travel.ui.me.identityauth.idauth.DriverAuthActivity;
import com.yunspeak.travel.ui.me.identityauth.idauth.IdAuthActivity;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.BR;


/**
 * Created by wangyang on 2017/3/30.
 */

public class IdentityAuthModel extends BaseObservable{
    private String id_card;
    private String run_card;
    private String driver_card;
    private String tel;

    public IdentityAuthModel() {
          refresh();
    }
    public void refresh(){
        UserInfo userInfo = GlobalUtils.getUserInfo();
        if (userInfo != null) {
            setTel(userInfo.getTel());
            setId_card(userInfo.getId_card());
            setRun_card(userInfo.getRun_card());
            setDriver_card(userInfo.getDrive_card());
        }
    }
   @Bindable
    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
        notifyPropertyChanged(BR.id_card);
    }
    @Bindable
    public String getRun_card() {
        return run_card;
    }

    public void setRun_card(String run_card) {
        this.run_card = run_card;
        notifyPropertyChanged(BR.run_card);
    }
    @Bindable
    public String getDriver_card() {
        return driver_card;
    }

    public void setDriver_card(String driver_card) {
        this.driver_card = driver_card;
        notifyPropertyChanged(BR.driver_card);
    }
   @Bindable
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        notifyPropertyChanged(BR.tel);
    }

    public void onClick(View view, String bindText) {
        Context context = view.getContext();
        if (view.getId() == R.id.rl_change_phone) {
            context.startActivity(new Intent(context, ChangePhoneActivity.class));
            return;
        }
        if (StringUtils.isEmpty(bindText) || bindText.equals("0")) {
            switch (view.getId()) {
                case R.id.rl_id_auth:
                    IdAuthActivity.start(context, true);
                    break;
                case R.id.rl_car:
                    IdAuthActivity.start(context, false);
                    break;
                case R.id.rl_dirver:
                    context.startActivity(new Intent(context, DriverAuthActivity.class));
                    break;
            }
        } else {
            ToastUtils.showToast("您已经认证过啦，无需再次认证。");
        }
    }
}
