package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class Fan {

    /**
     * code :
     * message :
     * data :
     */

    private int code;
    private String message;
    /**
     * nick_name :
     * user_img :
     * id : 2
     */

    private List<FanPeople> data;

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

    public List<FanPeople> getData() {
        return data;
    }

    public void setData(List<FanPeople> data) {
        this.data = data;
    }

    public static class FanPeople {
        private String nick_name;
        private String user_img;
        private String id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
