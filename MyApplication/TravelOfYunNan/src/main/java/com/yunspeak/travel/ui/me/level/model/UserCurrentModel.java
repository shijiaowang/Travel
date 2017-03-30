package com.yunspeak.travel.ui.me.level.model;

/**
 * Created by wangyang on 2017/3/30.
 * 用户当前经验
 */

public class UserCurrentModel {

        private String id;
        private String nick_name;
        private String user_img;
        private String level;
        private int level_score;
        private int level_max_score;

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

    public int getLevel_score() {
        return level_score;
    }

    public void setLevel_score(int level_score) {
        this.level_score = level_score;
    }

    public int getLevel_max_score() {
        return level_max_score;
    }

    public void setLevel_max_score(int level_max_score) {
        this.level_max_score = level_max_score;
    }
}
