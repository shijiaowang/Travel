package com.example.administrator.travel.ui.me.myappoint.chat;

import com.hyphenate.easeui.domain.UserInfo;

import java.util.List;

/**
 * Created by wangyang on 2016/10/20 0020.
 */

public class ChatBean {

    private int code;
    private String message;

    private List<UserInfo> data;

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

    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }


}
