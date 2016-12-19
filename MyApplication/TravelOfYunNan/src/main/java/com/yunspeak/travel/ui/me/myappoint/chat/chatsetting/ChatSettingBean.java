package com.yunspeak.travel.ui.me.myappoint.chat.chatsetting;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatSettingBean extends TravelsObject {

    private List<ChatSettingUserBean> data;



    public List<ChatSettingUserBean> getData() {
        return data;
    }

    public void setData(List<ChatSettingUserBean> data) {
        this.data = data;
    }


}
