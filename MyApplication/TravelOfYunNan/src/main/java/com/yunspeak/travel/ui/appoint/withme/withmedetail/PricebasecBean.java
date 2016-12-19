package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public  class PricebasecBean extends TravelsObject {
    private String id;
    private String key;
    private String value;
    private String type;
    private String time;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

