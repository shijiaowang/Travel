package com.yunspeak.travel.bean;

/**
 * Created by wangyang on 2017/2/22.
 */

public class CityNameBean {
    private int _id;
    private String name;
    private int upId;
    private String index;
    private String pinYin;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpId() {
        return upId;
    }

    public void setUpId(int upId) {
        this.upId = upId;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "CityNameBean{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", upId=" + upId +
                ", index='" + index + '\'' +
                ", pinYin='" + pinYin + '\'' +
                '}';
    }
}
