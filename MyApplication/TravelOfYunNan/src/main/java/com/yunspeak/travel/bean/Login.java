package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.bean.UserInfo;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 登录，包含用户信息
 */
public class Login extends TravelsObject {
    private UserInfo data;


    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }


}
