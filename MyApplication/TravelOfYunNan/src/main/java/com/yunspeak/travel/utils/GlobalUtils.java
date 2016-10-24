package com.yunspeak.travel.utils;

import android.content.Context;

import com.yunspeak.travel.bean.Login;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class GlobalUtils {
    public static String getKey(Context context){
        if (StringUtils.isEmpty(GlobalValue.KEY_VALUE)){
            GlobalValue.KEY_VALUE=context.getSharedPreferences(IVariable.SHARE_NAME,Context.MODE_PRIVATE).getString(IVariable.KEY_VALUE,"");
        }
        return GlobalValue.KEY_VALUE;
    }
    public static UserInfo getUserInfo(){
        if (GlobalValue.userInfo==null){
            GlobalValue.userInfo= UserUtils.getUserInfo();
        }
        return GlobalValue.userInfo;
    }
}
