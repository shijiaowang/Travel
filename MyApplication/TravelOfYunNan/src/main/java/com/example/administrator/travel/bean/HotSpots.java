package com.example.administrator.travel.bean;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class HotSpots {
    private String hotSpotsName;
    private String addr;//地址
    private ImageView picture;

    public String getHotSpotsName() {
        return hotSpotsName;
    }

    public void setHotSpotsName(String hotSpotsName) {
        this.hotSpotsName = hotSpotsName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}
