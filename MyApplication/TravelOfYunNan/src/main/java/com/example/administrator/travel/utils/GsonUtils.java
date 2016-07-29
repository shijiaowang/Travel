package com.example.administrator.travel.utils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class GsonUtils<T>{
    /**
     * 返回数据
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObject(String result,Class<T> clazz){
        Gson gson=new Gson();
        T t = gson.fromJson(result,clazz);
        return t;
    }
}
