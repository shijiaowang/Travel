package com.yunspeak.travel.ui.me.titlemanage;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/27 0027.
 * 删除事件
 */

public class TitleDeleteEvent extends TravelsObject {
    private String type;
    private String id;

    public TitleDeleteEvent(String type, String id) {
        this.type = type;
        this.id = id;
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
