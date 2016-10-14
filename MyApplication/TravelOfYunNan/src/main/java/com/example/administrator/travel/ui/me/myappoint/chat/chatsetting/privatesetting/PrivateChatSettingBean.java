package com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.privatesetting;

import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingUserBean;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class PrivateChatSettingBean {


    private int code;
    private String message;
    private ChatSettingUserBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatSettingUserBean getData() {
        return data;
    }

    public void setData(ChatSettingUserBean data) {
        this.data = data;
    }

}
