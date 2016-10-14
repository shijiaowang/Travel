package com.example.administrator.travel.ui.me.othercenter.useralbum;

import com.example.administrator.travel.ui.me.myhobby.UserLabelBean;
import com.example.administrator.travel.ui.me.othercenter.UserBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class OtherAlbumBean {

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

        public static class MoreBean {
            private String id;
            private String user_id;
            private String title;
            private String content;
            private String add_time;
            private String status;
            private String cover_img;
            private String browse;
            private String background_img;
            private String update_time;
            private String like;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public String getBrowse() {
                return browse;
            }

            public void setBrowse(String browse) {
                this.browse = browse;
            }

            public String getBackground_img() {
                return background_img;
            }

            public void setBackground_img(String background_img) {
                this.background_img = background_img;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getLike() {
                return like;
            }

            public void setLike(String like) {
                this.like = like;
            }
        }
    }
}
