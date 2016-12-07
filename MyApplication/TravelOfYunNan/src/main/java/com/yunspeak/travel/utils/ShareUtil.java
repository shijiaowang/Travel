package com.yunspeak.travel.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class ShareUtil {


    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static int getInt(Context context,String key,int def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,def);
    }
    public static String getString(Context context,String key,String def){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,def);
    }
    public static void putInt(Context context,String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
    public static void deleteData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        GlobalValue.KEY_VALUE="";
        GlobalValue.userInfo=null;
    }
}
