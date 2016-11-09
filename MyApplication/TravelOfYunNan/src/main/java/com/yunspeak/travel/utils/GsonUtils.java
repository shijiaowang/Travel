package com.yunspeak.travel.utils;

import com.google.gson.Gson;

/**
 * Created by wanyang on 2016/7/29 0029.
 */
public class GsonUtils{
    /**
     * 返回数据
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObject(String result,Class<T> clazz){
        T t=null;
        try {
            Gson gson = new Gson();
             t = gson.fromJson(result, clazz);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.e("json解析出错了");
        }
        return t;
    }
}
