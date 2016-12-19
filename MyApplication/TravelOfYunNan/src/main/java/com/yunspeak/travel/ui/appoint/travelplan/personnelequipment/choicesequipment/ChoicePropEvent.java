package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/9/18 0018.
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
