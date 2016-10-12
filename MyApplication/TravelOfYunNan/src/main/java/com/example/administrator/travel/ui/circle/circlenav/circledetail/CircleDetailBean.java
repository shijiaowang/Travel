package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import com.example.administrator.travel.global.IChildParent;
import com.example.administrator.travel.global.ParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/16 0016.
 */
public class CircleDetailBean implements ParentBean{



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

    public static class DataBean implements IChildParent<DataBean.BodyBean>{


        private HeadBean head;


        private List<BodyBean> body;

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }



        public static class HeadBean {
            private String cid;
            private String cname;
            private String circle_img;
            private String title_img;
            private String circle_ico;
            private String title;
            private String count_follow;
            private String count_forum;
            private String is_follow;

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

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }

            public String getCircle_ico() {
                return circle_ico;
            }

            public void setCircle_ico(String circle_ico) {
                this.circle_ico = circle_ico;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
            }
        }

        public static class BodyBean {
            private String id;
            private String cid;
            private String user_id;
            private String title;
            private String content;
            private String inform;
            private String forum_img;
            private String time;
            private String status;
            private String is_hot;
            private String update_time;
            private String nick_name;
            private String user_img;
            private String cname;
            private String count_reply;
            private String count_like;
            private String is_like;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getInform() {
                return inform;
            }

            public void setInform(String inform) {
                this.inform = inform;
            }

            public String getForum_img() {
                return forum_img;
            }

            public void setForum_img(String forum_img) {
                this.forum_img = forum_img;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public String getCount_reply() {
                return count_reply;
            }

            public void setCount_reply(String count_reply) {
                this.count_reply = count_reply;
            }

            public String getCount_like() {
                return count_like;
            }

            public void setCount_like(String count_like) {
                this.count_like = count_like;
            }

            public String getIs_like() {
                return is_like;
            }

            public void setIs_like(String is_like) {
                this.is_like = is_like;
            }
        }
    }
}
