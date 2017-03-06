package com.yunspeak.travel.bean;

import simpledao.cityoff.com.easydao.annotation.TableName;
import simpledao.cityoff.com.easydao.annotation.UpdateKey;

/**
 * Created by wangyang on 2017/3/2.
 * 数据库 user对象保存
 */
@TableName("user")
public class User {
    public User(){

    }
    public User(String json, String id, String lastLoginTime, String isLogin) {
        this.json = json;
        this.id = id;
        this.lastLoginTime = lastLoginTime;
        this.isLogin = isLogin;
    }

    public User(String json, String id, String name, String password, String lastLoginTime, String isLogin) {
        this.json = json;
        this.id = id;
        this.name = name;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.isLogin = isLogin;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getJson() {

        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String json;//保存网络拉取的用户json数据
    @UpdateKey
    private String id;//用户id 唯一标识 其余详细详细反射json获得
    @UpdateKey
    private String name;//用户账号 唯一标识 其余详细详细反射json获得
    private String password;//用户id 唯一标识 其余详细详细反射json获得
    private String lastLoginTime;//用户最后登录时间
    private String isLogin="0";//是否为登录状态 0 未登录 1登录
}
