package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class CircleDetailEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
