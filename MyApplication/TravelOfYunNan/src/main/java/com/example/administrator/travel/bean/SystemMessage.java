package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessage {
    private int iconBg;
    private String iconName;
    private String title="";
    private String time;
    private int number;
    private boolean isCursorItem=false;
    private boolean isLast=false;
    private String content;
    private String cursorContent;

    public String getCursorContent() {
        return cursorContent;
    }

    public void setCursorContent(String cursorContent) {
        this.cursorContent = cursorContent;
    }

    public int getIconBg() {
        return iconBg;
    }

    public void setIconBg(int iconBg) {
        this.iconBg = iconBg;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCursorItem() {
        return isCursorItem;
    }

    public void setIsCursorItem(boolean isCursorItem) {
        this.isCursorItem = isCursorItem;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
