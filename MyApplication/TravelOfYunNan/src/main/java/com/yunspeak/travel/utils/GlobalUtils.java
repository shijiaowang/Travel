package com.yunspeak.travel.utils;

import android.content.Context;

import com.yunspeak.travel.bean.User;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.UserDao;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;

import simpledao.cityoff.com.easydao.BaseDaoFactory;

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
            GlobalValue.userInfo= BaseDaoFactory.getInstance().getDaoHelper(UserDao.class, User.class).getCurrentUser();
        }
        return GlobalValue.userInfo;
    }
}
