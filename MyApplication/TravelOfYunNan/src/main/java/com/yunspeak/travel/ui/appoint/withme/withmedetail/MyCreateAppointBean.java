package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/11/7 0007.
 */

public class MyCreateAppointBean extends TravelsObject {


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
        private String add_time;
        private String total_price;
        private String routes;
        private int is_push;
        private boolean isSelect=false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getRoutes() {
            return routes;
        }

        public void setRoutes(String routes) {
            this.routes = routes;
        }

        public int getIs_push() {
            return is_push;
        }

        public void setIs_push(int is_push) {
            this.is_push = is_push;
        }
    }
}
