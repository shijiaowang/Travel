package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 登录，包含用户信息
 */
public class Login {

    private int code;
    private String message;

    private UserInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }


}
