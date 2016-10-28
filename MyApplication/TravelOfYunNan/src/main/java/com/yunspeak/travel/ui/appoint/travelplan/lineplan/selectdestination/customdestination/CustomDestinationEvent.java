package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import com.yunspeak.travel.event.HttpEvent;

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
