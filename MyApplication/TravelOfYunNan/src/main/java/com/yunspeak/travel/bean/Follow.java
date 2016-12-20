package com.yunspeak.travel.bean;

import java.io.Serializable;

/**
 * Created by wangyang on 2016/9/19 0019.
 */

    public  class Follow  implements Serializable{
        private String nick_name;
        private String user_img;
        private String id;
        private String content;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

}
