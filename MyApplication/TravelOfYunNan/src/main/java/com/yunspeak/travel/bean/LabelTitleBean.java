package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/28 0028.
 */
public  class LabelTitleBean extends TravelsObject {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}