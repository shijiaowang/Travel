package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogether {

    /**
     * code : 1
     * message : 加载成功
     * data : [{"id":"6","travel_img":"http://192.168.1.38","star_time":"1472608800","end_time":"1472799600","meet_address":"成都","max_people":"5","label":"实名达人,美食,徒步","total_price":"420.00","browse":"0","now_people":"0","count_like":"0","is_like":"0","routes":[{"title":"牛逼山"},{"title":"牛逼山"}]},{"id":"4","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","star_time":"1472140800","end_time":"1472400000","meet_address":"大苏打","max_people":"5","label":"实名达人,美食,徒步","total_price":"471.00","browse":"0","now_people":"2","count_like":"0","is_like":"0","routes":[{"title":"牛逼山"},{"title":"测试1"},{"title":"牛逼山"}]},{"id":"5","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57985a17124fe.jpg","star_time":"1472140800","end_time":"1472140800","meet_address":"巍峨我","max_people":"5","label":"实名达人","total_price":"111.00","browse":"0","now_people":"0","count_like":"0","is_like":"0","routes":[{"title":"牛逼山"},{"title":"测试1"},{"title":"牛逼山"}]},{"id":"3","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","star_time":"1472054400","end_time":"1472140800","meet_address":"成都","max_people":"6","label":"实名达人,购物","total_price":"420.00","browse":"0","now_people":"2","count_like":"0","is_like":"0","routes":[{"title":"牛逼山"},{"title":"测试1"}]},{"id":"2","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57985a17124fe.jpg","star_time":"1472054400","end_time":"1472140800","meet_address":"1","max_people":"5","label":"","total_price":"0.00","browse":"0","now_people":"0","count_like":"0","is_like":"0","routes":""}]
     */

    private int code;
    private String message;
    /**
     * id : 6
     * travel_img : http://192.168.1.38
     * star_time : 1472608800
     * end_time : 1472799600
     * meet_address : 成都
     * max_people : 5
     * label : 实名达人,美食,徒步
     * total_price : 420.00
     * browse : 0
     * now_people : 0
     * count_like : 0
     * is_like : 0
     * routes : [{"title":"牛逼山"},{"title":"牛逼山"}]
     */

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
        private String star_time;
        private String end_time;
        private String meet_address;
        private String max_people;
        private String label;
        private String total_price;
        private String browse;
        private String now_people;
        private String count_like;
        private String is_like;
        /**
         * title : 牛逼山
         */

        private List<RoutesBean> routes;

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

        public String getStar_time() {
            return star_time;
        }

        public void setStar_time(String star_time) {
            this.star_time = star_time;
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

        public List<RoutesBean> getRoutes() {
            return routes;
        }

        public void setRoutes(List<RoutesBean> routes) {
            this.routes = routes;
        }

        public static class RoutesBean {
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
