package com.example.administrator.travel.ui.me.messagecenter.relateme;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public class RelateMeBean {


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
        private String reply;
        private String inform;
        private String like;

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getInform() {
            return inform;
        }

        public void setInform(String inform) {
            this.inform = inform;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }
    }
}
