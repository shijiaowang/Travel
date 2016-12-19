package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/18 0018.
 * 选中的
 */
public class ChoicePropSelectBean extends TravelsObject {
    private String id;
    private int number;
    private String name;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
