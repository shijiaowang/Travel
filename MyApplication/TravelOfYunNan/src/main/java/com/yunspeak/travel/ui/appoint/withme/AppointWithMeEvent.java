package com.yunspeak.travel.ui.appoint.withme;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/1 0001.
 */
public class AppointWithMeEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
