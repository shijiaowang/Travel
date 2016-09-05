package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherDetail {


    /**
     * code : 1
     * message : 加载成功
     * data : {"id":"4","user_id":"10000","title":"123123","content":"wqeqweqweq","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","star_time":"1472140800","end_time":"1472400000","traffic":"火车","traffic_text":"哦请问恶趣味额·","car_type":"","min_people":"2","max_people":"5","sex_condition":"2","bind_condition":"3","agree":"1","price":"111.00","total_price":"471.00","label":"实名达人,美食,徒步","meet_address":"大苏打","over_address":"发送多个地方","add_time":"1472436752","status":"1","state":"1","management":"10000","browse":"0","id_code":"","into_people":[{"id":"10000","user_img":"http://192.168.1.38/Uploads/0.png","nick_name":"yuns客服"},{"id":"10009","user_img":"http://192.168.1.38/Uploads/1.png","nick_name":"我叫王小"}],"now_people":2,"ing_people":"","count_like":"0","is_like":"0","routes":[{"time":"1472140800","id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"},{"time":"1472140800","id":"2","title":"测试1","content":"测试","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"贵州省","city":"曲靖","address":"21312阿斯大声大气"},{"time":"1472227200","id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"}],"prop":[{"id":"3","name":"加长超级摄像头","content":"","number":"2"}],"pricebasec":[{"id":"1","key":"平台服务费","value":"20","type":"1","time":"1469586077","status":"1"},{"id":"2","key":"个人旅游险","value":"100","type":"1","time":"1472024625","status":"1"}]}
     */

    private int code;
    private String message;
    /**
     * id : 4
     * user_id : 10000
     * title : 123123
     * content : wqeqweqweq
     * travel_img : http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg
     * star_time : 1472140800
     * end_time : 1472400000
     * traffic : 火车
     * traffic_text : 哦请问恶趣味额·
     * car_type :
     * min_people : 2
     * max_people : 5
     * sex_condition : 2
     * bind_condition : 3
     * agree : 1
     * price : 111.00
     * total_price : 471.00
     * label : 实名达人,美食,徒步
     * meet_address : 大苏打
     * over_address : 发送多个地方
     * add_time : 1472436752
     * status : 1
     * state : 1
     * management : 10000
     * browse : 0
     * id_code :
     * into_people : [{"id":"10000","user_img":"http://192.168.1.38/Uploads/0.png","nick_name":"yuns客服"},{"id":"10009","user_img":"http://192.168.1.38/Uploads/1.png","nick_name":"我叫王小"}]
     * now_people : 2
     * ing_people :
     * count_like : 0
     * is_like : 0
     * routes : [{"time":"1472140800","id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"},{"time":"1472140800","id":"2","title":"测试1","content":"测试","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"贵州省","city":"曲靖","address":"21312阿斯大声大气"},{"time":"1472227200","id":"1","title":"牛逼山","content":"就是很厉害很厉害很来还的挥洒大时代和","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","province":"云南省","city":"成都市","address":"昆明周边路由大家上帝阿迪"}]
     * prop : [{"id":"3","name":"加长超级摄像头","content":"","number":"2"}]
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
        private String traffic;
        private String traffic_text;
        private String car_type;
        private String min_people;
        private String max_people;
        private String sex_condition;
        private String bind_condition;
        private String agree;
        private String price;
        private String total_price;
        private String label;
        private String meet_address;
        private String over_address;
        private String add_time;
        private String status;
        private String state;
        private String management;
        private String browse;
        private String id_code;
        private int now_people;
        private String ing_people;
        private String count_like;
        private String is_like;
        /**
         * id : 10000
         * user_img : http://192.168.1.38/Uploads/0.png
         * nick_name : yuns客服
         */

        private List<IntoPeopleBean> into_people;
        /**
         * time : 1472140800
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
         * id : 3
         * name : 加长超级摄像头
         * content :
         * number : 2
         */

        private List<PropBean> prop;
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

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public String getTraffic_text() {
            return traffic_text;
        }

        public void setTraffic_text(String traffic_text) {
            this.traffic_text = traffic_text;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getMin_people() {
            return min_people;
        }

        public void setMin_people(String min_people) {
            this.min_people = min_people;
        }

        public String getMax_people() {
            return max_people;
        }

        public void setMax_people(String max_people) {
            this.max_people = max_people;
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

        public String getAgree() {
            return agree;
        }

        public void setAgree(String agree) {
            this.agree = agree;
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

        public String getOver_address() {
            return over_address;
        }

        public void setOver_address(String over_address) {
            this.over_address = over_address;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getManagement() {
            return management;
        }

        public void setManagement(String management) {
            this.management = management;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public String getId_code() {
            return id_code;
        }

        public void setId_code(String id_code) {
            this.id_code = id_code;
        }

        public int getNow_people() {
            return now_people;
        }

        public void setNow_people(int now_people) {
            this.now_people = now_people;
        }

        public String getIng_people() {
            return ing_people;
        }

        public void setIng_people(String ing_people) {
            this.ing_people = ing_people;
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

        public List<IntoPeopleBean> getInto_people() {
            return into_people;
        }

        public void setInto_people(List<IntoPeopleBean> into_people) {
            this.into_people = into_people;
        }

        public List<RoutesBean> getRoutes() {
            return routes;
        }

        public void setRoutes(List<RoutesBean> routes) {
            this.routes = routes;
        }

        public List<PropBean> getProp() {
            return prop;
        }

        public void setProp(List<PropBean> prop) {
            this.prop = prop;
        }

        public List<PricebasecBean> getPricebasec() {
            return pricebasec;
        }

        public void setPricebasec(List<PricebasecBean> pricebasec) {
            this.pricebasec = pricebasec;
        }

        public static class IntoPeopleBean {
            private String id;
            private String user_img;
            private String nick_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }
        }

        public static class RoutesBean {
            private String time;
            private String id;
            private String title;
            private String content;
            private String logo_img;
            private String province;
            private String city;
            private String address;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

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

        public static class PropBean {
            private String id;
            private String name;
            private String content;
            private String number;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }

        public static class PricebasecBean {
            private String id;
            private String key;
            private String value;
            private String type;
            private String time;
            private String status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
