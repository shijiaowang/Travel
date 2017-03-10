package com.yunspeak.travel.ui.home.welcome.home.model;

import android.databinding.BaseObservable;

/**
 * Created by wangyang on 2017/3/9.
 */

public  class IndexTextBean extends BaseObservable{
    private String id;
    private String type;
    private String title;
    private String img;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}