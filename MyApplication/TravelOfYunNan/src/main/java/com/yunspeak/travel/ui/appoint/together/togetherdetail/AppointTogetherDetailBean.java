package com.yunspeak.travel.ui.appoint.together.togetherdetail;


import com.yunspeak.travel.bean.PeopleBean;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.PricebasecBean;

import java.util.List;

/**
 * Created by wangyang on 2016/7/20 0020.
 */
public class AppointTogetherDetailBean {



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
        private String id;
        private String user_id;
        private String action;
        private String title;
        private String content;
        private String travel_img;
        private String start_time;
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
        private String user_name;
        private String user_img;
        private String sex;
        private int now_people;
        private String count_like;
        private String is_like;
        private String is_collect;
        private String routes_title;
        private String share_url;
        private String basectext;

        public String getBasectext() {
            return basectext;
        }

        public void setBasectext(String basectext) {
            this.basectext = basectext;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        private List<PeopleBean> into_people;


        private List<PeopleBean> ing_people;


        private List<RoutesBean> routes;

        private List<PropBean> prop;


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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
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

        public String getRoutes_title() {
            return routes_title;
        }

        public void setRoutes_title(String routes_title) {
            this.routes_title = routes_title;
        }

        public List<PeopleBean> getInto_people() {
            return into_people;
        }

        public void setInto_people(List<PeopleBean> into_people) {
            this.into_people = into_people;
        }

        public List<PeopleBean> getIng_people() {
            return ing_people;
        }

        public void setIng_people(List<PeopleBean> ing_people) {
            this.ing_people = ing_people;
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
    }
}
