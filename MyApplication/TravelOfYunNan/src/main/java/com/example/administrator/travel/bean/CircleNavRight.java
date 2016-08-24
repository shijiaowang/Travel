package com.example.administrator.travel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRight {

    /**
     * code : 1
     * message : 加载成功
     * data : [{"cid":"5","cname":"骑行","circle_img":"http://192.168.1.158/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","circle_ico":"http://192.168.1.158/Uploads/Picture/2016-07-27/579859dec9a96.png","count_follow":"1","count_forum":"0"}]
     */

    private int code;
    private String message;
    /**
     * cid : 5
     * cname : 骑行
     * circle_img : http://192.168.1.158/Uploads/Picture/2016-07-27/579859e0d84f6.jpg
     * circle_ico : http://192.168.1.158/Uploads/Picture/2016-07-27/579859dec9a96.png
     * count_follow : 1
     * count_forum : 0
     */

    private List<RightCircle> data;

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

    public List<RightCircle> getData() {
        return data;
    }

    public void setData(List<RightCircle> data) {
        this.data = data;
    }

    public static class RightCircle extends TravelObjBean {
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
