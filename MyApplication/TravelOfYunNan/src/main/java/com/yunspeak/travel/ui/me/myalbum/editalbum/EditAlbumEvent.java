package com.yunspeak.travel.ui.me.myalbum.editalbum;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/27 0027.
 */

public class EditAlbumEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
