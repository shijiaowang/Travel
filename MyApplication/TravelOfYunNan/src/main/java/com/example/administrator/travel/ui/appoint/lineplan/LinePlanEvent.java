package com.example.administrator.travel.ui.appoint.lineplan;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class LinePlanEvent {
    private int position;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private boolean isDelete=false;
    private LineBean.Destination destination;

    public LineBean.Destination getDestination() {
        return destination;
    }

    public void setDestination(LineBean.Destination destination) {
        this.destination = destination;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }


}
