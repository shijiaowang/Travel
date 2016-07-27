package com.example.administrator.travel.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class Login {
    /**
     * code : 1
     * message : 成功
     * data : {"id":"1","tel":"18281614311","pwd":"14e1b600b1fd579f47433b88e8d85291","nick_name":"18281614311","name":"","sex":"1","birthday":"0000-00-00 00:00:00","email":"","id_card":"","user_img":"","add_time":"0"}
     */

    private int code;
    private String message;
    /**
     * id : 1
     * tel : 18281614311
     * pwd : 14e1b600b1fd579f47433b88e8d85291
     * nick_name : 18281614311
     * name :
     * sex : 1
     * birthday : 0000-00-00 00:00:00
     * email :
     * id_card :
     * user_img :
     * add_time : 0
     */

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

    public static class UserInfo implements Serializable{
        private String id;
        private String tel;
        private String pwd;
        private String nick_name;
        private String name;
        private String sex;
        private String birthday;
        private String email;
        private String id_card;
        private String user_img;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}