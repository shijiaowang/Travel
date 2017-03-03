package com.hyphenate.easeui.domain;

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
        private int id;
        @RenameField("nickName")
        private String nick_name;
        @RenameField("userImg")
        private String user_img;

    public UserInfo(int id) {
        this.id = id;
    }

    public UserInfo() {
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
        public boolean isEquals(UserInfo userInfo){
            if (userInfo==null || this.id==0 || this.nick_name==null || this.user_img==null)return false;
            return this.id==userInfo.getId() && this.user_img.equals(userInfo.getUser_img())
                    && this.nick_name.equals(userInfo.getNick_name())
                    ;
        }

}
