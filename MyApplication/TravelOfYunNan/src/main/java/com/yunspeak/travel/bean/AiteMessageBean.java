package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/11/8 0008.
 */

public class AiteMessageBean extends TravelsObject implements ParentBean{


    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String floor;
        private int type;
        private String user_img;
        private String nick_name;
        private String content;
        private String forum_id;
        private String reply_time;
        private String title;
        private String title_desc;
        private String img;
        private String url;
        private String user_id;
        private String cid;
        private String pid;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private List<InformBean> inform;

        public int getFloor() {
            try {
                return Integer.parseInt(floor);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }

        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getForum_id() {
            return forum_id;
        }

        public void setForum_id(String forum_id) {
            this.forum_id = forum_id;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<InformBean> getInform() {
            return inform;
        }

        public void setInform(List<InformBean> inform) {
            this.inform = inform;
        }
    }
}
