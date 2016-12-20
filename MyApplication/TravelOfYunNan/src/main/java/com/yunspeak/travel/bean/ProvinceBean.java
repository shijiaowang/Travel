package com.yunspeak.travel.bean;


import com.bigkoo.pickerview.model.IPickerViewData;
import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 15/11/22.
 */
public class ProvinceBean extends TravelsObject implements IPickerViewData {
    private String id;
    private String name;


    public ProvinceBean(String id, String name){
        this.id = id;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //这个用显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }
}
