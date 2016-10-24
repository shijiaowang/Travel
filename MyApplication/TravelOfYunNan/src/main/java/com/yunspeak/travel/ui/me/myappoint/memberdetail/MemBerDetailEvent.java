package com.yunspeak.travel.ui.me.myappoint.memberdetail;

import com.yunspeak.travel.event.HttpEvent;

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
