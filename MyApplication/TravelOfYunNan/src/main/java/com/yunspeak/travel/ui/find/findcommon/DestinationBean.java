package com.yunspeak.travel.ui.find.findcommon;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/24 0024.
 * 目的地
 */
public class DestinationBean implements ParentBean{


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

        private List<CityBean> province;

        private List<CityBean> city;

        private List<BodyBean> body;

        public List<CityBean> getProvince() {
            return province;
        }

        public void setProvince(List<CityBean> province) {
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