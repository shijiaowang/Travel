package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/8/17 0017.
 * 圈子关注
 */
public class CircleFollowBean extends TravelsObject {


    private DataBean data;
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
