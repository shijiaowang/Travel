package com.example.administrator.travel.bean;


import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by Administrator on 2016/7/18 0018.
 * 粉丝和关注
 */
public class AiteFollow {
    private String nikeName = "";


    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }



}
