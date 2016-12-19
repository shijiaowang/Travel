package com.yunspeak.travel.ui.appoint.together;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/1 0001.
 */
public class AppointTogetherEvent extends HttpEvent {

    private int clickPosition;

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }
}
