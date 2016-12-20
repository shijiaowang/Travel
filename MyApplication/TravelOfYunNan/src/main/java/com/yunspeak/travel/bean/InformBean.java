package com.yunspeak.travel.bean;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/11/3 0003.
 */

public  class InformBean implements Serializable{
    private String id;
    private String nick_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
