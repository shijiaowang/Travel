package com.yunspeak.travel.ui.me.level;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class LevelUserBean extends TravelsObject {


    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private UserBean user;

        private List<LevelBean> level;

        private List<LevelDescBean> level_desc;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<LevelBean> getLevel() {
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public List<LevelDescBean> getLevel_desc() {
            return level_desc;
        }

        public void setLevel_desc(List<LevelDescBean> level_desc) {
            this.level_desc = level_desc;
        }

        public static class UserBean {
            private String id;
            private String nick_name;
            private String user_img;
            private String level;
            private String level_score;
            private String level_max_score;

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
        }

        public static class LevelBean {
            private String id;
            private String content;

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

        public static class LevelDescBean {
            private String title;
            private String score;
            private String max_score;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getMax_score() {
                return max_score;
            }

            public void setMax_score(String max_score) {
                this.max_score = max_score;
            }
        }
    }
}
