package com.yunspeak.travel.ui.me.myappoint;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/7 0007.
 * 约伴订单-找人带
 */

public class MyAppointWithMeBean extends TravelsObject implements ParentBean {



    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String user_id;
        private String title;
        private String content;
        private String travel_img;
        private String start_time;
        private String end_time;
        private String sex_condition;
        private String bind_condition;
        private String price;
        private String total_price;
        private String label;
        private String meet_address;
        private String add_time;
        private String status;
        private String browse;
        private String state;
        private String user_count;
        private String routes;
        private String count_like;
        private String is_like;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getTravel_img() {
            return travel_img;
        }

        public void setTravel_img(String travel_img) {
            this.travel_img = travel_img;
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

        public String getSex_condition() {
            return sex_condition;
        }

        public void setSex_condition(String sex_condition) {
            this.sex_condition = sex_condition;
        }

        public String getBind_condition() {
            return bind_condition;
        }

        public void setBind_condition(String bind_condition) {
            this.bind_condition = bind_condition;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getMeet_address() {
            return meet_address;
        }

        public void setMeet_address(String meet_address) {
            this.meet_address = meet_address;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUser_count() {
            return user_count;
        }

        public void setUser_count(String user_count) {
            this.user_count = user_count;
        }

        public String getRoutes() {
            return routes;
        }

        public void setRoutes(String routes) {
            this.routes = routes;
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
