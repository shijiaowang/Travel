package com.example.administrator.travel.utils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class JsonUtils {
    public static JSONObject basecObject;
    public static JSONObject routesObject;
    public static JSONObject propObject;
    public  static JSONObject getBasecJsonObject(){
        if (basecObject==null){
            basecObject= new JSONObject();
        }
        return basecObject;
    }
    public  static JSONObject getRoutesJsonObject(){
        if (routesObject==null){
            routesObject= new JSONObject();
        }
        return routesObject;
    }
    public  static JSONObject getPropJsonObject(){
        if (propObject==null){
            propObject= new JSONObject();
        }
        return propObject;
    }

    /**
     * 输入的数据如果为空就抛出异常，方便之后确保不能为空的value，之后直接捕获异常即可
     * @param key
     * @param value
     * @param jsonObject
     * @throws Exception
     */
    public static void putString(String key,String value,JSONObject jsonObject) throws Exception {
        if (jsonObject==null)return;
        if (StringUtils.isEmpty(value))throw  new Exception("数据为空啦");
        jsonObject.put(key,value);
    }

    /**
     * 重置
     */
    public static void reset(){
        basecObject=null;
        propObject=null;
        routesObject=null;
    }
}
