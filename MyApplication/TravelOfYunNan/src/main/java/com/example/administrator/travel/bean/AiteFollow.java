package com.example.administrator.travel.bean;


import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by Administrator on 2016/7/18 0018.
 * 粉丝和关注
 */
public class AiteFollow {
    private String nikeName = "";
    public char indexChar;
    public boolean isFirst=true;


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



}
