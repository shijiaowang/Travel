package com.yunspeak.travel.ui.circle.hot.model;

import com.yunspeak.travel.bean.HotPostBean;
import com.yunspeak.travel.bean.InformBean;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/9/23 0023.
 * 热帖
 */

public class Hot extends TravelsObject{

    private List<HotPostModel> data;

    public List<HotPostModel> getData() {
        return data;
    }

    public void setData(List<HotPostModel> data) {
        this.data = data;
    }


}
