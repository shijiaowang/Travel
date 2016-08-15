package com.example.administrator.travel.utils;
import android.content.Context;
import com.example.administrator.travel.global.IVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MapUtils {
     static Map<String,String> stringMap=new HashMap<>();
    public static Builder Build(){
        stringMap.clear();
        return new Builder();
    }
    public static class Builder{
        public Builder addKey(Context context){
            stringMap.put(IVariable.KEY,GlobalUtils.getKey(context));
            return this;
        }
        public Builder add(String key,String value){
            stringMap.put(key,value);
            return this;
        }
        public Map<String,String> end(){
            return stringMap;
        }
    }
}
