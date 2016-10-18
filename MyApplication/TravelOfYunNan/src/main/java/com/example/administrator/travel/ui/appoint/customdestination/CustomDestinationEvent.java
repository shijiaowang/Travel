package com.example.administrator.travel.ui.appoint.customdestination;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/9 0009.
 */
public class CustomDestinationEvent extends HttpEvent {
    private int deletePosition;

    public int getDeletePosition() {
        return deletePosition;
    }

    public void setDeletePosition(int deletePosition) {
        this.deletePosition = deletePosition;
    }


}
