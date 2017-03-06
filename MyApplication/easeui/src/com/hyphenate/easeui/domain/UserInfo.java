package com.hyphenate.easeui.domain;

import android.text.TextUtils;

import java.io.Serializable;

import simpledao.cityoff.com.easydao.annotation.RenameField;
import simpledao.cityoff.com.easydao.annotation.TableName;
import simpledao.cityoff.com.easydao.annotation.UpdateKey;

/**
 * Created by wangyang on 2016/10/22 0022.
 * 聊天用的用户对象用于环信三方显示头像昵称信息
 */
@TableName("chat_user")
public class UserInfo implements Serializable{
        @UpdateKey
        private String userId;
        @RenameField("nickName")
        private String nick_name;
        @RenameField("userImg")
        private String user_img;

    public UserInfo(String userId) {
        this.userId = userId;
    }

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

}
