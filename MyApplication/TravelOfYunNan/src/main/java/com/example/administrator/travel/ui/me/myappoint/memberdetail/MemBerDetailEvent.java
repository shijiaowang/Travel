package com.example.administrator.travel.ui.me.myappoint.memberdetail;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/7 0007.
 */

public class MemBerDetailEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
