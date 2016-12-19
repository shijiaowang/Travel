package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.InformBean;

import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class PostBean extends TravelsObject implements ParentBean {




    private List<DataBean> data;



    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String cid;
        private String c_id;
        private String title;
        private String content;
        private String forum_img;
        private String cname;
        private String add_time;
        private String id;

        private List<InformBean> inform;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
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

        public String getForum_img() {
            return forum_img;
        }

        public void setForum_img(String forum_img) {
            this.forum_img = forum_img;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<InformBean> getInform() {
            return inform;
        }

        public void setInform(List<InformBean> inform) {
            this.inform = inform;
        }

    }
}
