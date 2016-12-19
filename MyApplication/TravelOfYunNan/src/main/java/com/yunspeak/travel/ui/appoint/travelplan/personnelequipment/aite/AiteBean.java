package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/9/19 0019.
 */
public class AiteBean extends TravelsObject implements ParentBean{


    private List<Follow> data;


    public List<Follow> getData() {
        return data;
    }

    public void setData(List<Follow> data) {
        this.data = data;
    }


}
