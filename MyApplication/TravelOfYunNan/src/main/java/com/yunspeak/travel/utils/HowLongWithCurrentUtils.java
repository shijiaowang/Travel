package com.yunspeak.travel.utils;

import java.util.Date;

/**
 * Created by wangyang on 2016/12/27 0027.
 */

public class HowLongWithCurrentUtils {
    public static final int SECOND=1000;//秒
    public static final int MINUTE =SECOND*60;//分钟
    public static final int HOUR =MINUTE*60;//小时
    public static final int DAY =HOUR*24;//天
    /**
     *
     * @param time 时间
     * @return 距离现在多长的时间描述
     */
    public static String getDesStringFromTime(String time){
         return getDesStringFromTime(time,null);
    }
    public static String getDesStringFromTime(String time,String rule){
        if (StringUtils.isEmpty(time)) return "未知时间";
        long inputTime = Long.parseLong(time + "000");//服务器返回的时间是到秒
        long currentTime=new Date().getTime();
        int howDay = CalendarUtils.getHowDay(inputTime + "",currentTime+ "")-1;//减去那边加一的值
        if (howDay==0){
            return changeLongTimeToDes(inputTime,currentTime,rule);
        }else if (howDay<7){
            return howDay+"天前";
        }else {
            return defaultTime(time, StringUtils.isEmpty(rule) ? "yyyy.MM.dd HH:mm:ss" : rule);
        }
    }

    private static String defaultTime(String time, String format) {
        return FormatDateUtils.FormatLongTime(format, time);
    }

    private static String changeLongTimeToDes(long inputTime, long currentTime,String format) {
        long temp=currentTime-inputTime;//时间差
        if (temp/SECOND<60){//一分钟内
            return "刚刚";
        }else if (temp/MINUTE<60){//一小时内
            return temp/MINUTE+"分钟前";
        }else if (temp/HOUR<24){//一天之内
            return temp/HOUR+"小时前";
        }
        return defaultTime(inputTime+"", format);
    }
}
