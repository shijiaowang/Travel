package com.yunspeak.travel.ui.me.mytheme;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;

import java.util.List;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public class MyPublishBean implements ParentBean{

    private int code;
    private String message;

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String f_id;
        private String content;
        private String add_time;
        private String type;
        private String title;
        private String img;
        private String id;
        private String cname;
        private String count_like;
        private int find_type;
        private int r_id;
        private List<InformBean> inform;

        public List<InformBean> getInform() {
            return inform;
        }

        public void setInform(List<InformBean> inform) {
            this.inform = inform;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        private int cid;


        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public String getF_id() {
            return f_id;
        }

        public void setF_id(String f_id) {
            this.f_id = f_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCount_like() {
            return count_like;
        }

        public void setCount_like(String count_like) {
            this.count_like = count_like;
        }

        public int getFind_type() {
            return find_type;
        }

        public void setFind_type(int find_type) {
            this.find_type = find_type;
        }
    }
}
