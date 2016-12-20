package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangtyang on 2016/7/12 0012.
 */
public class FindActiveBean extends TravelsObject implements ParentBean{

    private List<ActivityBean> data;


    public List<ActivityBean> getData() {
        return data;
    }

    public void setData(List<ActivityBean> data) {
        this.data = data;
    }


}
