package com.hyphenate.easeui.domain;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/10/22 0022.
 */

public class UserInfo implements Serializable{
        private String id;
        private String nick_name;
        private String user_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

}
