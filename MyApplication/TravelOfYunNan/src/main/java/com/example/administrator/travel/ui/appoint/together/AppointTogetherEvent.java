package com.example.administrator.travel.ui.appoint.together;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by Administrator on 2016/9/1 0001.
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
