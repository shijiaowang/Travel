package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/8/17 0017.
 * 点赞
 */
public class CircleLike {

    /**
     * code : 1
     * message : 点赞成功
     * data : {"count_like":"1"}
     */

    private int code;
    private String message;
    /**
     * count_like : 1
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
        private String count_like;

        public String getCount_like() {
            return count_like;
        }

        public void setCount_like(String count_like) {
            this.count_like = count_like;
        }
    }
}
