package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.bean.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingBean extends TravelsObject{

    private List<BasecPriceBean> data;
    private String basectext;

    public String getBasectext() {
        return basectext;
    }

    public void setBasectext(String basectext) {
        this.basectext = basectext;
    }

    public List<BasecPriceBean> getData() {
        return data;
    }

    public void setData(List<BasecPriceBean> data) {
        this.data = data;
    }


}
