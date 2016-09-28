package com.example.administrator.travel.ui.me.collectiondetail;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class CollectionDetailEvent extends HttpEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
