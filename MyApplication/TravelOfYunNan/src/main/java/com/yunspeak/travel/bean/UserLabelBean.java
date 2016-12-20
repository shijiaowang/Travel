package com.yunspeak.travel.bean;

import com.google.gson.annotations.SerializedName;
import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/28 0028.
 */
public  class UserLabelBean  extends TravelsObject {
    private String id;
    private String name;
    private String type;
    @SerializedName("class")
    private String classX;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }
}