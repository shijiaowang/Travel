package com.yunspeak.travel.ui.me.myalbum;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/9/26 0026.
 */

public class MyAlbumBean implements ParentBean{



    private int code;
    private String message;


    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String user_id;
        private String title;
        private String content;
        private String add_time;
        private String status;
        private String cover_img;
        private int count;
        private String browse;
        private String like;
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
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

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }
    }
}
