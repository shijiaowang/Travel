package com.example.administrator.travel.ui.appoint.aite;

import com.example.administrator.travel.global.ParentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class AiteBean {


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
