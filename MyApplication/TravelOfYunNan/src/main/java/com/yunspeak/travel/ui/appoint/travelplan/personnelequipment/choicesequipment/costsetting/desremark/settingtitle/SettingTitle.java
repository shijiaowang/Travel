package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.settingtitle;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/9/6 0006.
 */
public class SettingTitle implements Serializable{
    private int type;
    private String title;
    private int changeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;



    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;
}
