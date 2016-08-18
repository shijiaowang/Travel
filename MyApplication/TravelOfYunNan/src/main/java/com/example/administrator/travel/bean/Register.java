package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/8/18 0018.
 * 注册
 */
public class Register {

    /**
     * code : 1
     * message : 注册成功
     * data : {"user_id":"10004"}
     */

    private int code;
    private String message;
    /**
     * user_id : 10004
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
