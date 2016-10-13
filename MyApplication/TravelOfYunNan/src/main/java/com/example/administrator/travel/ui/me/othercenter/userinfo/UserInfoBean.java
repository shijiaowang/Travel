package com.example.administrator.travel.ui.me.othercenter.userinfo;

import com.example.administrator.travel.ui.me.myhobby.UserLabelBean;
import com.example.administrator.travel.ui.me.othercenter.UserBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class UserInfoBean {

    private int code;
    private String message;

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
        private UserBean user;

        private List<UserLabelBean> user_label;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

    }
    }

