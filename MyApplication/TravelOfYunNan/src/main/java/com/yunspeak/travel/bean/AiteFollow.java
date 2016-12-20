package com.yunspeak.travel.bean;


import com.github.promeg.pinyinhelper.Pinyin;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/7/18 0018.
 * 粉丝和关注
 */
public class AiteFollow implements Serializable{
    private String nikeName = "";
    public char indexChar;
    public boolean isFirst=true;
    private Follow follow;

    public Follow getFollow() {
        return follow;
    }



    public void setFollow(Follow follow) {
        this.follow = follow;
        setNikeName(follow.getNick_name());
        this.isFirst=true;
    }

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
    public char getIndexChar(){
        if (isFirst){
            char first = this.nikeName.charAt(0);
            char index = Pinyin.toPinyin(first).charAt(0);
            indexChar=isLowCaseAndChangeBigCase(index);
            isFirst=false;
        }
        return indexChar;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    /**
     * 小写转换为大写
     * @param c
     */
    public char isLowCaseAndChangeBigCase(char c) {
        char heightA='A';
        char a = 'a';
        char z = 'z';
        if (a <= c && c <= z) {
            String string = Character.toString(c);
            c = string.toUpperCase().charAt(0);
        }
        /**
         * 将数字和特殊字符放在最后
         */
        if (c < heightA) {
            c = '{';
        }
        return c;
    }


    public boolean equalsFollow(Follow o) {

        if (this.follow==null || o==null){
            return false;
        }
        return this.follow.getId().equals(o.getId());
    }
}
