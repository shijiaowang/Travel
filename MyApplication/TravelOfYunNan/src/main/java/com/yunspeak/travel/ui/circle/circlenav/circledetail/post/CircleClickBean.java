package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

/**
 * Created by wangyang on 2016/11/9 0009.
 */

public class CircleClickBean {



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
        private String count_like;

        public String getCount_like() {
            return count_like;
        }

        public void setCount_like(String count_like) {
            this.count_like = count_like;
        }
    }
}
