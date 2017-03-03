package com.yunspeak.travel.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import simpledao.cityoff.com.easydao.annotation.TableName;

/**
 * Created by wangyang on 2017/2/22.
 * 城市名称
 */
@TableName("yuns_district")
public class CityNameBean implements IPickerViewData {
    private int _id;//城市id
    private String name;//城市名称
    private int upId;//城市上一级别 0省直辖市等 1市
    private String index;//第一个大写字母
    private String pinYin;//全拼音
    private int level;//当前级别 0省直辖市等 1市

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

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

    /**
     * 城市列表需要
     * @return 返回城市名称
     */
    @Override
    public String getPickerViewText() {
        return name;
    }
}
