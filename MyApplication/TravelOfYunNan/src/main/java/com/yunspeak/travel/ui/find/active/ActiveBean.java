package com.yunspeak.travel.ui.find.active;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.ActivityBean;

import java.util.List;

/**
 * Created by wangtyang on 2016/7/12 0012.
 */
public class ActiveBean implements ParentBean{


    private int code;
    private String message;

    private List<ActivityBean> data;

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

    public List<ActivityBean> getData() {
        return data;
    }

    public void setData(List<ActivityBean> data) {
        this.data = data;
    }


}
