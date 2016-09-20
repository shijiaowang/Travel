package com.example.administrator.travel.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class FormatDateUtils {
    /**
     * 格式化时间
     * @param format 规则
     * @param time 时间
     * @return
     */
    public static String FormatLongTime(String format,String time){
        try {
            time=time+"000";//秒转化为毫秒
            long l = Long.parseLong(time);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
            return simpleDateFormat.format(l);
        }catch (Exception e){
            return time;
        }
    }
}
