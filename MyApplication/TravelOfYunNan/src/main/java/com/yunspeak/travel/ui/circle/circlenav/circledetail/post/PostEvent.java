package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class PostEvent extends HttpEvent {
    public String url;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
