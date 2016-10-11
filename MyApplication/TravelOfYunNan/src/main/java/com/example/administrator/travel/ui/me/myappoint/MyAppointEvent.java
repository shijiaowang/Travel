package com.example.administrator.travel.ui.me.myappoint;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/1.
 */

public class MyAppointEvent extends HttpEvent {
    private int position;
    private int payStatus;



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }
}
