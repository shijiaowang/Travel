package com.yunspeak.travel.global;



import java.util.List;

/**
 * Created by wangyang on 2017/3/17.
 */

public  class ListBean<T> extends TravelsObject {
    public List<T> data;

    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }

}
