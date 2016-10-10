package com.example.administrator.travel.ui.me.myappoint.withmeselect;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class MyWithMeSelectEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

