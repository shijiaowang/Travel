package com.yunspeak.travel.ui.appoint;

/**
 * Created by wangyang on 2016/11/7 0007.
 */

public class SelectEvent {
    private String label="";
    private String timePosition="";
    private String orderPosition="";

    public SelectEvent(String label, String timePosition, String orderPosition) {
        this.label = label;
        this.timePosition = timePosition;
        this.orderPosition = orderPosition;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTimePosition() {
        return timePosition;
    }

    public void setTimePosition(String timePosition) {
        this.timePosition = timePosition;
    }

    public String getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(String orderPosition) {
        this.orderPosition = orderPosition;
    }
}
