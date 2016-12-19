package com.yunspeak.travel.ui.me.myappoint.chat;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/10/22 0022.
 */

public class ChatFragmentEvent extends TravelsObject {
    public boolean isGetMessage=false;

    public boolean isGetMessage() {
        return isGetMessage;
    }

    public void setGetMessage(boolean getMessage) {
        isGetMessage = getMessage;
    }
}
