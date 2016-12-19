package com.yunspeak.travel.ui.find.active;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.ActivityBean;

import java.util.List;

/**
 * Created by wangtyang on 2016/7/12 0012.
 */
public class ActiveBean  extends TravelsObject implements ParentBean{

    private List<ActivityBean> data;


    public List<ActivityBean> getData() {
        return data;
    }

    public void setData(List<ActivityBean> data) {
        this.data = data;
    }


}
