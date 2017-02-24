package com.yunspeak.travel.bean;

import java.util.Arrays;

/**
 * Created by wangyang on 2017/2/23.
 */
public class MonthBean {
    private int startIndex;
    private int maxDay;
    private int endIndex;
    private int[] dayIndex=new int[42];
    private int currentDay;
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int[] getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int[] dayIndex) {
        this.dayIndex = dayIndex;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    @Override
    public String toString() {
        return "MonthBean{" +
                "startIndex=" + startIndex +
                ", maxDay=" + maxDay +
                ", endIndex=" + endIndex +
                ", dayIndex=" + Arrays.toString(dayIndex) +
                ", currentDay=" + currentDay +
                ", month='" + month + '\'' +
                '}';
    }
}
