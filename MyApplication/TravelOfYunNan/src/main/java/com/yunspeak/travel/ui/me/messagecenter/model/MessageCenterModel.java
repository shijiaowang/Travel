package com.yunspeak.travel.ui.me.messagecenter.model;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageActivity;
import com.yunspeak.travel.ui.me.messagecenter.privatemessage.MessagePrivateActivity;
import com.yunspeak.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import com.yunspeak.travel.ui.me.messagecenter.systemmessage.SystemMessageActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.BR;


/**
 * Created by wangyang on 2017/3/30.
 */

public class MessageCenterModel extends BaseObservable{

    private int travel;
    private int system;
    private int letter;
    private int user;
    private int message;
    public MessageCenterModel(){
        refreshMessage();
    }

    public void refreshMessage(){
        message=AiteUtils.getUnReadMessage();
    }
    @Bindable
    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
        notifyPropertyChanged(BR.travel);
    }
   @Bindable
    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
        notifyPropertyChanged(BR.system);
    }

    public int getLetter() {
        return letter;
    }

    public void setLetter(int letter) {
        this.letter = letter;
    }
   @Bindable
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }
    @Bindable
    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    public void onClick(View v) {
        Context context = v.getContext();
        switch (v.getId()) {
            case R.id.ll_system_message:
                context.startActivity(new Intent(context, SystemMessageActivity.class));
                setSystem(0);
                break;
            case R.id.ll_appoint_message:
                context.startActivity(new Intent(context, AppointMessageActivity.class));
                setTravel(0);
                break;
            case R.id.ll_private:
                context.startActivity(new Intent(context, MessagePrivateActivity.class));
                break;
            case R.id.ll_relate_me:
                context.startActivity(new Intent(context, RelateMeActivity.class));
                setUser(0);
                break;
        }
    }
}
