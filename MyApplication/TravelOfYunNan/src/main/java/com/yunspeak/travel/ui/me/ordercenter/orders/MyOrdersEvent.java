package com.yunspeak.travel.ui.me.ordercenter.orders;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class MyOrdersEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
