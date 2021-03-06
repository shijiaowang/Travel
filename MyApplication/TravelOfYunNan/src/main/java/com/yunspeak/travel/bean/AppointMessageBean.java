package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/11/8 0008.
 */

public class AppointMessageBean extends TravelsObject implements ParentBean{


    private List<DataBean> data;



    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String user_img;
        private String nick_name;
        private String user_id;
        private String content;
        private String tid;
        private String reply_time;
        private String title;
        private String title_desc;
        private String type;
        private String travel_img;
        private String pid;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_desc() {
            return title_desc;
        }

        public void setTitle_desc(String title_desc) {
            this.title_desc = title_desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTravel_img() {
            return travel_img;
        }

        public void setTravel_img(String travel_img) {
            this.travel_img = travel_img;
        }
    }
}
