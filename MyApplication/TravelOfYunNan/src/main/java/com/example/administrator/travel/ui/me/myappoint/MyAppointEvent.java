package com.example.administrator.travel.ui.me.myappoint;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/1.
 */

public class MyAppointEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
