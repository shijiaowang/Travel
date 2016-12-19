package com.yunspeak.travel.ui.circle.circlenav;


import com.yunspeak.travel.global.TravelsObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class CircleNavRight extends TravelsObject {


    private List<RightCircle> data;

    public List<RightCircle> getData() {
        return data;
    }

    public void setData(List<RightCircle> data) {
        this.data = data;
    }

    public static class RightCircle implements Serializable {
        private String cid;
        private String cname;
        private String circle_img;
        private String circle_ico;
        private String count_follow;
        private String count_forum;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCircle_img() {
            return circle_img;
        }

        public void setCircle_img(String circle_img) {
            this.circle_img = circle_img;
        }

        public String getCircle_ico() {
            return circle_ico;
        }

        public void setCircle_ico(String circle_ico) {
            this.circle_ico = circle_ico;
        }

        public String getCount_follow() {
            return count_follow;
        }

        public void setCount_follow(String count_follow) {
            this.count_follow = count_follow;
        }

        public String getCount_forum() {
            return count_forum;
        }

        public void setCount_forum(String count_forum) {
            this.count_forum = count_forum;
        }
    }
}
