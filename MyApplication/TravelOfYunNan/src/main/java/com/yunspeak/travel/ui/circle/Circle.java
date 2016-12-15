package com.yunspeak.travel.ui.circle;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/7/11 0011.
 */
public class Circle {


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


        private List<CircleLeftBean> circle_left;


        private List<CircleRightBean> circle_right;

        public List<CircleLeftBean> getCircle_left() {
            return circle_left;
        }

        public void setCircle_left(List<CircleLeftBean> circle_left) {
            this.circle_left = circle_left;
        }

        public List<CircleRightBean> getCircle_right() {
            return circle_right;
        }

        public void setCircle_right(List<CircleRightBean> circle_right) {
            this.circle_right = circle_right;
        }

        public static class CircleLeftBean implements Serializable {
            private String cid;
            private String cname;
            public boolean isCheck=false;

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
        }

        public static class CircleRightBean {
            private String cid;
            private String cname;
            private String circle_img;
            private String circle_ico;
            private String add_time;
            private String pid;
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

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
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
}
