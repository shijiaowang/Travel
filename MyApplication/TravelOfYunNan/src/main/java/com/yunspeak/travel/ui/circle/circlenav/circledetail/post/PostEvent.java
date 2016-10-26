package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class PostEvent extends HttpEvent {
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
