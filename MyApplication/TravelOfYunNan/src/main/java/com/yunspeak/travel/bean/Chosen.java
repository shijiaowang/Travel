package com.yunspeak.travel.bean;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 精选
 */
public class Chosen {
    private String Type;//主题类型
    private String pictureUrl;//展示图片
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
