package com.example.administrator.travel.ui.me.othercenter;

import com.example.administrator.travel.ui.me.myhobby.UserLabelBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class OtherUserCenterBean {

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
        private UserBean user;


        private List<MoreBean> more;
        private List<UserLabelBean> user_label;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<MoreBean> getMore() {
            return more;
        }

        public void setMore(List<MoreBean> more) {
            this.more = more;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

        public static class MoreBean implements Serializable{
            private String id;
            private String cid;
            private String time;
            private String title;
            private String type;
            private String content;
            private String user_img;
            private String nick_name;
            private String cname;
            private String re_user;
            private String fid;
            private String count_like;
            private String is_like;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getCount_like() {
                return count_like;
            }

            public void setCount_like(String count_like) {
                this.count_like = count_like;
            }

            public String getIs_like() {
                return is_like;
            }

            public void setIs_like(String is_like) {
                this.is_like = is_like;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public String getRe_user() {
                return re_user;
            }

            public void setRe_user(String re_user) {
                this.re_user = re_user;
            }
        }


    }
}
