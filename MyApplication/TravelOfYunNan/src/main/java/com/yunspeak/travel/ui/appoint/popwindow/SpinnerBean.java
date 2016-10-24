package com.yunspeak.travel.ui.appoint.popwindow;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class SpinnerBean {
    private String type;
    private String id;
    private int spinnerId;

    public int getSpinnerId() {
        return spinnerId;
    }

    public void setSpinnerId(int spinnerId) {
        this.spinnerId = spinnerId;
    }

    public SpinnerBean(String type, String id, int spinnerId) {
        this.type = type;
        this.id = id;
        this.spinnerId = spinnerId;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
