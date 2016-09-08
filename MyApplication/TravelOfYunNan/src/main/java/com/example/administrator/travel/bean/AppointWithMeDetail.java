package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class AppointWithMeDetail {


    /**
     * code : 1
     * message : 加载成功
     * data : {"id":"3","user_id":"10009","title":"第一次出现","content":"第一次出现内容","travel_img":"http://192.168.1.38","star_time":"1472608800","end_time":"1472799600","sex_condition":"2","bind_condition":"2","price":"400.00","total_price":"420.00","label":"实名达人,美食,徒步","meet_address":"成都","add_time":"1473230822","status":"1","browse":"0","user_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","sex":"1","people":[{"id":"10009","user_img":"http://192.168.1.38/Uploads/1.png","nick_name":"我叫王小"},{"id":"10000","user_img":"http://192.168.1.38/Uploads/0.png","nick_name":"yuns客服"}],"count_like":"0","is_like":"0","routes_title":"牛逼山-牛逼山","routes":[{"id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"},{"id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"}],"pricebasec":[{"id":"1","key":"平台服务费","value":"20","type":"1","time":"1469586077","status":"1"},{"id":"2","key":"个人旅游险","value":"100","type":"1","time":"1472024625","status":"1"}]}
     */

    private int code;
    private String message;
    /**
     * id : 3
     * user_id : 10009
     * title : 第一次出现
     * content : 第一次出现内容
     * travel_img : http://192.168.1.38
     * star_time : 1472608800
     * end_time : 1472799600
     * sex_condition : 2
     * bind_condition : 2
     * price : 400.00
     * total_price : 420.00
     * label : 实名达人,美食,徒步
     * meet_address : 成都
     * add_time : 1473230822
     * status : 1
     * browse : 0
     * user_name : 我叫王小
     * user_img : http://192.168.1.38/Uploads/1.png
     * sex : 1
     * people : [{"id":"10009","user_img":"http://192.168.1.38/Uploads/1.png","nick_name":"我叫王小"},{"id":"10000","user_img":"http://192.168.1.38/Uploads/0.png","nick_name":"yuns客服"}]
     * count_like : 0
     * is_like : 0
     * routes_title : 牛逼山-牛逼山
     * routes : [{"id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"},{"id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"}]
     * pricebasec : [{"id":"1","key":"平台服务费","value":"20","type":"1","time":"1469586077","status":"1"},{"id":"2","key":"个人旅游险","value":"100","type":"1","time":"1472024625","status":"1"}]
     */

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
        private String id;
        private String user_id;
        private String title;
        private String content;
        private String travel_img;
        private String star_time;
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
        private String user_name;
        private String user_img;
        private String sex;
        private String count_like;
        private String is_like;
        private String routes_title;
        /**
         * id : 10009
         * user_img : http://192.168.1.38/Uploads/1.png
         * nick_name : 我叫王小
         */

        private List<PeopleBean> people;
        /**
         * id : 1
         * title : 牛逼山
         * content : 就是很厉害很厉害很来还的挥洒大时代和
         * logo_img : http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg
         * province : 云南省
         * city : 成都市
         * address : 昆明周边路由大家上帝阿迪
         */

        private List<RoutesBean> routes;
        /**
         * id : 1
         * key : 平台服务费
         * value : 20
         * type : 1
         * time : 1469586077
         * status : 1
         */

        private List<PricebasecBean> pricebasec;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getRoutes_title() {
            return routes_title;
        }

        public void setRoutes_title(String routes_title) {
            this.routes_title = routes_title;
        }

        public List<PeopleBean> getPeople() {
            return people;
        }

        public void setPeople(List<PeopleBean> people) {
            this.people = people;
        }

        public List<RoutesBean> getRoutes() {
            return routes;
        }

        public void setRoutes(List<RoutesBean> routes) {
            this.routes = routes;
        }

        public List<PricebasecBean> getPricebasec() {
            return pricebasec;
        }

        public void setPricebasec(List<PricebasecBean> pricebasec) {
            this.pricebasec = pricebasec;
        }



        public static class RoutesBean {
            private String id;
            private String title;
            private String content;
            private String logo_img;
            private String province;
            private String city;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
