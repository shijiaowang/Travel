package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/12/12 0012.
 * 插入的活动详情
 */

public  class ActivityBean extends TravelsObject {
    private String id;
    private String title;
    private String activity_img;
    private String now_people;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity_img() {
        return activity_img;
    }

    public void setActivity_img(String activity_img) {
        this.activity_img = activity_img;
    }

    public String getNow_people() {
        return now_people;
    }

    public void setNow_people(String now_people) {
        this.now_people = now_people;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
