package com.example.administrator.travel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class PhoneUtils {
    public static final String REGEX_MOBILE = "^1[3,4,5,7,8]\\d{9}$";
    public static boolean isMobileNO(String mobiles) {
        if (mobiles==null){
            return false;
        }
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(mobiles);
        boolean matches = m.matches();
        return matches;

    }

}
