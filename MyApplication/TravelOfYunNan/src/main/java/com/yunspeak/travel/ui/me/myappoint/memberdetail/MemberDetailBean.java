package com.yunspeak.travel.ui.me.myappoint.memberdetail;

import com.yunspeak.travel.global.DataParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/7 0007.
 */

public class MemberDetailBean {


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
        private String is_boss;


        private List<JoinBean> joined;


        private List<JoinBean> join_ing;

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }

        public List<JoinBean> getJoined() {
            return joined;
        }

        public void setJoined(List<JoinBean> joined) {
            this.joined = joined;
        }

        public List<JoinBean> getJoin_ing() {
            return join_ing;
        }

        public void setJoin_ing(List<JoinBean> join_ing) {
            this.join_ing = join_ing;
        }

        public static class JoinBean implements DataParentBean{
            private String status;
            private String state;
            private String content;
            private String user_img;
            private String nick_name;
            private String id;
            private String sex;
            private String age;
            private String add_time;
            private String is_boss;
            private String is_manage;
            private String user_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getIs_manage() {
                return is_manage;
            }

            public void setIs_manage(String is_manage) {
                this.is_manage = is_manage;
            }

            public String getIs_boss() {
                return is_boss;
            }

            public void setIs_boss(String is_boss) {
                this.is_boss = is_boss;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
        }

    }
}
