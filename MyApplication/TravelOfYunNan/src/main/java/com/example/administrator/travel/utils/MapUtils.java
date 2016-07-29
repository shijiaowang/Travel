package com.example.administrator.travel.utils;

import android.content.Context;

import com.example.administrator.travel.global.IVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MapUtils {
     Map<String,String> stringMap=new HashMap<>();
    public  MapUtils getConmmonMap(Context context){
        stringMap.put(IVariable.KEY,KeyUtils.getKey(context));
        return this;
    }
    public  MapUtils addParams(String key,String value){
        stringMap.put(key,value);
        return this;
    }
}
