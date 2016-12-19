package com.yunspeak.travel.ui.home;

import com.yunspeak.travel.global.TravelsObject;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/9/20 0020.
 */
public class UserInfo extends TravelsObject implements Serializable {


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
    private String background_img;
    private String content;
    private String status;
    private String type;
    private String level;
    private String level_score;
    private String level_max_score;
    private String add_time;
    private String update_time;
    private int birthday_see;

    public String getId() {
        return id;
    }

    public int getBirthday_see() {
        return birthday_see;
    }

    public void setBirthday_see(int birthday_see) {
        this.birthday_see = birthday_see;
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

    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel_score() {
        return level_score;
    }

    public void setLevel_score(String level_score) {
        this.level_score = level_score;
    }

    public String getLevel_max_score() {
        return level_max_score;
    }

    public void setLevel_max_score(String level_max_score) {
        this.level_max_score = level_max_score;
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

