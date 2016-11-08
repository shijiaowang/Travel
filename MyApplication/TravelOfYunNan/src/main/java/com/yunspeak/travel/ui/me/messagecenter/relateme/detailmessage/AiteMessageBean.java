package com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/8 0008.
 */

public class AiteMessageBean implements ParentBean{


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
        private String floor;
        private int type;
        private String user_img;
        private String nick_name;
        private String content;
        private String f_id;
        private String reply_time;
        private String title;
        private String title_desc;
        private String img;

        private List<InformBean> inform;

        public String getFloor() {
            return floor;
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

        public String getF_id() {
            return f_id;
        }

        public void setF_id(String f_id) {
            this.f_id = f_id;
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
