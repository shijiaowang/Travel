package com.example.administrator.travel.ui.me.me;

import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.ui.me.myhobby.UserLabelBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangyang on 2016/10/1.
 */

public class MeBean {


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
        private UserInfo user;
        private String follow;
        private String fans;
        private int count_msg;

        private List<UserLabelBean> user_label;

        public UserInfo getUser() {
            return user;
        }

        public void setUser(UserInfo user) {
            this.user = user;
        }

        public String getFollow() {
            return follow;
        }

        public void setFollow(String follow) {
            this.follow = follow;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public int getCount_msg() {
            return count_msg;
        }

        public void setCount_msg(int count_msg) {
            this.count_msg = count_msg;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

    }
}
