package com.yunspeak.travel.utils;

import android.content.Context;

import com.yunspeak.travel.ui.home.UserInfo;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;

/**
 * Created by wangyang on 2016/7/29 0029.
 */
public class GlobalUtils {
    public static String getKey(){
        if (StringUtils.isEmpty(GlobalValue.KEY_VALUE)){
            GlobalValue.KEY_VALUE=UIUtils.getContext().getSharedPreferences(IVariable.SHARE_NAME,Context.MODE_PRIVATE).getString(IVariable.KEY_VALUE,"");
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
