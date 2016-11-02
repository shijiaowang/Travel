package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

/**
 * Created by wangyang on 2016/11/2 0002.
 */

public class DeleteAiteEvent {
    int position;

    public DeleteAiteEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
