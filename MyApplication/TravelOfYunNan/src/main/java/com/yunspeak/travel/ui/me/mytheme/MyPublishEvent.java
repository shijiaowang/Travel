package com.yunspeak.travel.ui.me.mytheme;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public class MyPublishEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
