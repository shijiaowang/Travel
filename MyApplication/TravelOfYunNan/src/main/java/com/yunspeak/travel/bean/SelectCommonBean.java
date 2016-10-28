package com.yunspeak.travel.bean;

/**
 * Created by Administrator on 2016/9/9 0009.
 * 选择用公共bean对象
 */
public class SelectCommonBean {
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SelectCommonBean(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    private boolean isChecked=false;
}
