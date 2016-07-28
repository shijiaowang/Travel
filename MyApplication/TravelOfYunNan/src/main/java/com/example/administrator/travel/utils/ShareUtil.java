package com.example.administrator.travel.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.travel.global.IVariable;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class ShareUtil {


    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static void putInt(Context context,String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IVariable.SHARE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
}
