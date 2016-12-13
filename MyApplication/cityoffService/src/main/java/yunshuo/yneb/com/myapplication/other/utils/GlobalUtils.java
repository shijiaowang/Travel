package yunshuo.yneb.com.myapplication.other.utils;

import android.content.Context;

import yunshuo.yneb.com.myapplication.GlobalValue;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.other.bean.UserInfo;


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
