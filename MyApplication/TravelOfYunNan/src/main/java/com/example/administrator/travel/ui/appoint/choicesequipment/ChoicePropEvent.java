package com.example.administrator.travel.ui.appoint.choicesequipment;

import com.example.administrator.travel.event.HttpEvent;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class ChoicePropEvent extends HttpEvent {
    private boolean isSelectEqu=false;

    public boolean isSelectEqu() {
        return isSelectEqu;
    }

    public void setIsSelectEqu(boolean isSelectEqu) {
        this.isSelectEqu = isSelectEqu;
    }
}
