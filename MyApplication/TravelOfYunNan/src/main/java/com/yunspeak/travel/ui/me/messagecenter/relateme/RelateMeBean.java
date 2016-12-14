package com.yunspeak.travel.ui.me.messagecenter.relateme;

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
        private int reply;
        private int inform;
        private int like;

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public int getInform() {
            return inform;
        }

        public void setInform(int inform) {
            this.inform = inform;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }
    }
}
