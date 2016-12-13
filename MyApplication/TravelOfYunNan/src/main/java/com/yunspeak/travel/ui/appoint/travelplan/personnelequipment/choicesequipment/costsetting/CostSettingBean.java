package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingBean {

    private int code;
    private String message;
    private List<BasecPriceBean> data;
    private String basectext;

    public String getBasectext() {
        return basectext;
    }

    public void setBasectext(String basectext) {
        this.basectext = basectext;
    }
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

    public List<BasecPriceBean> getData() {
        return data;
    }

    public void setData(List<BasecPriceBean> data) {
        this.data = data;
    }


}
