package com.yunspeak.travel.ui.me.myappoint;


import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/1.
 */

public class MyAppointTogetherBean extends TravelsObject implements ParentBean {


    private List<DataBean> data;



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
        private String max_people;
        private String id_code;
        private String total_price;
        private String state;
        private String add_time;
        private String user_id;
        private String pay_type;
        private String order_id;
        private String groupid;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public int getBulletin() {
            return bulletin;
        }

        public void setBulletin(int bulletin) {
            this.bulletin = bulletin;
        }

        private int bulletin;
        private String browse;
        private String status;
        private String is_boss;
        private int now_people;
        private String count_like;
        private String is_like;
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

        public String getMax_people() {
            return max_people;
        }

        public void setMax_people(String max_people) {
            this.max_people = max_people;
        }

        public String getId_code() {
            return id_code;
        }

        public void setId_code(String id_code) {
            this.id_code = id_code;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }

        public int getNow_people() {
            return now_people;
        }

        public void setNow_people(int now_people) {
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

        public String getRoutes() {
            return routes;
        }

        public void setRoutes(String routes) {
            this.routes = routes;
        }
    }
}
