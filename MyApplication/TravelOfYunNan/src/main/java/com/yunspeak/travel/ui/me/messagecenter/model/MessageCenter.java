package com.yunspeak.travel.ui.me.messagecenter.model;


import com.yunspeak.travel.bean.MessageCenterBean;
import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2017/3/30.
 */

public class MessageCenter extends TravelsObject {
    private MessageCenterModel data;

    public MessageCenterModel getData() {
        return data;
    }

    public void setData(MessageCenterModel data) {
        this.data = data;
    }
}
