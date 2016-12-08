package com.yunspeak.travel.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wangyang on 2016/9/9 0009.
 */
public class JsonUtils {
    public static JSONObject basecObject;
    public static JSONArray routesArray;
    public static JSONArray propArray;
    public  static JSONObject getBasecJsonObject(){
        if (basecObject==null){
            basecObject= new JSONObject();
        }
        return basecObject;
    }
    public  static JSONArray getRoutesJsonArray(){
        if (routesArray ==null){
            routesArray = new JSONArray();
        }
        return routesArray;
    }
    public  static JSONArray getPropJsonArray(){
        if (propArray ==null){
            propArray = new JSONArray();
        }
        return propArray;
    }

    public static JSONArray getNewJsonArray(){
        return routesArray = new JSONArray();
    }

    public static JSONArray getPropArray() {
        return propArray;
    }

    public static void setPropArray(JSONArray propArray) {
        JsonUtils.propArray = propArray;
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
        propArray =null;
        routesArray =null;
    }
}
