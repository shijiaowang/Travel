package com.example.administrator.travel.bean;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 精选
 */
public class Chosen {
    private String Type;//主题类型
    private ImageView picture;//展示图片
    private String desc;//描述内容

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}
