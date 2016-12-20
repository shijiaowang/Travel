package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatSettingUserBean extends TravelsObject {
        private String id;
        private String nick_name;
        private String user_img;
        private String content;
        private int is_boss;
        private int is_management;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(int is_boss) {
            this.is_boss = is_boss;
        }

        public int getIs_management() {
            return is_management;
        }

        public void setIs_management(int is_management) {
            this.is_management = is_management;
        }

}
