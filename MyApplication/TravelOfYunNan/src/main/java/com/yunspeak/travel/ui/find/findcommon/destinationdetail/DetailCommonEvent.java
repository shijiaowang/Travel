package com.yunspeak.travel.ui.find.findcommon.destinationdetail;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class DetailCommonEvent extends HttpEvent {
    private int clickPosition;//点赞的position

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }
}
