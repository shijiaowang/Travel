package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class Line {
    private String dayNumber;
    private String dayTime;
    private List<String> add;

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public List<String> getAdd() {
        return add;
    }

    public void setAdd(List<String> add) {
        this.add = add;
    }
}
