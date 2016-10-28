package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class AiteBean implements ParentBean{


    private int code;
    private String message;

    private List<Follow> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Follow> getData() {
        return data;
    }

    public void setData(List<Follow> data) {
        this.data = data;
    }


}
