package com.example.administrator.travel.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 登录，包含用户信息
 */
public class Login {

    /**
     * code : 1
     * message : 成功
     * data : {"id":"10009","tel":"15828358360","name":"17608009032","pwd":"14e1b600b1fd579f47433b88e8d85291","nick_name":"我叫王小","sex":"1","birthday":"0000-00-00 00:00:00","province":"0","city":"0","email":"","id_card":"","drive_card":"0","run_card":"0","user_img":"","content":"张飞在此","status":"1","add_time":"1471577459","update_time":"1471594877"}
     */

    private int code;
    private String message;
    /**
     * id : 10009
     * tel : 15828358360
     * name : 17608009032
     * pwd : 14e1b600b1fd579f47433b88e8d85291
     * nick_name : 我叫王小
     * sex : 1
     * birthday : 0000-00-00 00:00:00
     * province : 0
     * city : 0
     * email :
     * id_card :
     * drive_card : 0
     * run_card : 0
     * user_img :
     * content : 张飞在此
     * status : 1
     * add_time : 1471577459
     * update_time : 1471594877
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

    public static class UserInfo extends TravelBean{
        private String id;
        private String tel;
        private String name;
        private String pwd;
        private String nick_name;
        private String sex;
        private String birthday;
        private String province;
        private String city;
        private String email;
        private String id_card;
        private String drive_card;
        private String run_card;
        private String user_img;
        private String content;
        private String status;
        private String add_time;
        private String update_time;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getDrive_card() {
            return drive_card;
        }

        public void setDrive_card(String drive_card) {
            this.drive_card = drive_card;
        }

        public String getRun_card() {
            return run_card;
        }

        public void setRun_card(String run_card) {
            this.run_card = run_card;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
