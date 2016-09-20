package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMe {

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
        private String id;
        private String travel_img;
        private String start_time;
        private String end_time;
        private String meet_address;
        private String label;
        private String total_price;
        private String browse;
        private String now_people;
        private String count_like;
        private String is_like;
        private String user_count;
        private String routes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getMeet_address() {
            return meet_address;
        }

        public void setMeet_address(String meet_address) {
            this.meet_address = meet_address;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public String getNow_people() {
            return now_people;
        }

        public void setNow_people(String now_people) {
            this.now_people = now_people;
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
    }
}
