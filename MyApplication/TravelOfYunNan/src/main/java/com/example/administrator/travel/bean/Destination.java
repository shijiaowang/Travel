package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 * 目的地
 */
public class Destination {


    /**
     * code : 1
     * message : 加载成功
     * data : {"province":[{"province":"12","name":"安徽省"},{"province":"15","name":"山东省"},{"province":"23","name":"四川省"},{"province":"24","name":"贵州省"},{"province":"25","name":"云南省"},{"province":"26","name":"西藏自治区"}],"city":[{"city":"385","name":"成都市","upid":"23"},{"city":"386","name":"自贡市","upid":"23"},{"city":"417","name":"曲靖","upid":"25"},{"city":"418","name":"玉溪","upid":"25"},{"city":"419","name":"保山","upid":"25"},{"city":"420","name":"昭通","upid":"25"},{"city":"530","name":"沙田区","upid":"33"}],"body":[{"id":"1","title":"牛逼山","province":"25","city":"385","star":"3","logo_img":"http://192.168.1.3810","address":"云南省成都市昆明周边路由大家上帝阿迪"},{"id":"2","title":"测试1","province":"24","city":"417","star":"3","logo_img":"http://192.168.1.3812","address":"贵州省曲靖21312阿斯大声大气"},{"id":"7","title":"测试2","province":"12","city":"418","star":"3","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png","address":"安徽省玉溪实打实电话卡上的卡号"},{"id":"8","title":"测试","province":"15","city":"419","star":"2","logo_img":"http://192.168.1.38","address":"山东省保山qweqwe"},{"id":"9","title":"231","province":"15","city":"420","star":"3","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg","address":"山东省昭通31231231"},{"id":"11","title":"测试景区1","province":"23","city":"530","star":"4","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png","address":"四川省沙田区你猜猜"}]}
     */

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
        /**
         * province : 12
         * name : 安徽省
         */

        private List<ProvinceBean> province;
        /**
         * city : 385
         * name : 成都市
         * upid : 23
         */

        private List<CityBean> city;
        /**
         * id : 1
         * title : 牛逼山
         * province : 25
         * city : 385
         * star : 3
         * logo_img : http://192.168.1.3810
         * address : 云南省成都市昆明周边路由大家上帝阿迪
         */

        private List<BodyBean> body;

        public List<ProvinceBean> getProvince() {
            return province;
        }

        public void setProvince(List<ProvinceBean> province) {
            this.province = province;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }

        public static class ProvinceBean {
            private String province;
            private String name;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CityBean {
            private String city;
            private String name;
            private String upid;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUpid() {
                return upid;
            }

            public void setUpid(String upid) {
                this.upid = upid;
            }
        }

        public static class BodyBean {
            private String id;
            private String title;
            private String province;
            private String city;
            private String star;
            private String logo_img;
            private String address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
