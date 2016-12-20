package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/11/9 0009.
 */

public class CircleClickBean extends TravelsObject {
    private DataBean data;
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
