package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class ActiveBean extends TravelsObject implements ParentBean<List<ActiveBean.DataBean>>{




    private List<ActiveBean.DataBean> data;

    public List<ActiveBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ActiveBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String cid;
        private String title;
        private String activity_img;
        private String start_time;
        private String end_time;
        private String price;
        private String add_time;
        private String id;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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
    }
}
