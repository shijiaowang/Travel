package com.yunspeak.travel.ui.me.fansandfollow;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/8/25 0025.
 */
public class FanAndFollowEvent extends HttpEvent {
    private int childType;

    public int getChildType() {
        return childType;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }
}
