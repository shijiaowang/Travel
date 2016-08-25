package com.example.administrator.travel.event;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class DestinationDetailEvent extends HttpEvent {
    private int clickPosition;//点赞的position

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }
}
