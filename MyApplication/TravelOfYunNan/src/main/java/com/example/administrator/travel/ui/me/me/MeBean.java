package com.example.administrator.travel.ui.me.me;

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
        /**
         * id : 10009
         * nick_name : 我叫王小
         * user_img : http://yuns.yunspeak.com/Uploads/1.png
         * content : 你再说什么
         * background_img : http://yuns.yunspeak.com/Uploads/Api/User/2016-09-27/57ea421a4ed56.jpg
         * id_card : 510902199107015956
         * drive_card : 0
         * run_card : 0
         * level : 0
         */

        private UserBean user;
        private String follow;
        private String fans;
        private int count_msg;
        /**
         * id : 19
         * name : 略游小成
         * type : 1
         * class : 1
         */

        private List<UserLabelBean> user_label;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
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

        public static class UserBean {
            private String id;
            private String nick_name;
            private String user_img;
            private String content;
            private String background_img;
            private String id_card;
            private String drive_card;
            private String run_card;
            private String level;

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

            public String getBackground_img() {
                return background_img;
            }

            public void setBackground_img(String background_img) {
                this.background_img = background_img;
            }

            public String getId_card() {
                return id_card;
            }

            public void setId_card(String id_card) {
                this.id_card = id_card;
            }

            public String getDrive_card() {
                return drive_card;
            }

            public void setDrive_card(String drive_card) {
                this.drive_card = drive_card;
            }

            public String getRun_card() {
                return run_card;
            }

            public void setRun_card(String run_card) {
                this.run_card = run_card;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }
        }

        public static class UserLabelBean {
            private String id;
            private String name;
            private String type;
            @SerializedName("class")
            private String classX;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getClassX() {
                return classX;
            }

            public void setClassX(String classX) {
                this.classX = classX;
            }
        }
    }
}
