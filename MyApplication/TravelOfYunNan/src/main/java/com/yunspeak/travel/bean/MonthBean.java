package com.yunspeak.travel.bean;

/**
 * Created by wangyang on 2017/2/23.
 */
public class MonthBean {
    private int startIndex;
    private int maxDay;
    private int endIndex;
    private int[] dayIndex=new int[42];
    private int currentDay;

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    private int currentYear;
    private int currentMonth;
    private String yearMonth;

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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

}
