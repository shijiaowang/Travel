package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/8/17 0017.
 * 圈子关注
 */
public class CircleFollow {

    /**
     * code : 1
     * message : 关注成功
     * data : {"follow_count":"2"}
     */

    private int code;
    private String message;
    /**
     * follow_count : 2
     */

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
        private String follow_count;

        public String getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }
    }
}
