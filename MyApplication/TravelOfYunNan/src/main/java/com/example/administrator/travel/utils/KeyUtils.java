package com.example.administrator.travel.utils;

import android.content.Context;

import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class KeyUtils {
    public static String getKey(Context context){
        if (StringUtils.isEmpty(GlobalValue.KEY_VALUE)){
            GlobalValue.KEY_VALUE=context.getSharedPreferences(IVariable.SHARE_NAME,Context.MODE_PRIVATE).getString(IVariable.KEY,"");
        }
        return GlobalValue.KEY_VALUE;
    }
}
