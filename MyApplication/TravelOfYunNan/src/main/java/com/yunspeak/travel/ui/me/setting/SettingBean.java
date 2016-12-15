package com.yunspeak.travel.ui.me.setting;


import com.yunspeak.travel.ui.home.UserInfo;

/**
 * Created by wangyang on 2016/11/5 0005.
 */

public class SettingBean {


    private int code;
    private String message;
    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }


}
