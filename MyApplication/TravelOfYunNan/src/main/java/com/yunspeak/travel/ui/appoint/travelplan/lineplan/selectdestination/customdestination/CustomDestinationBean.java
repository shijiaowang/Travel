package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class CustomDestinationBean  implements ParentBean{

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
        private String title;
        private String logo_img;
        private String content;
        private String province;
        private String city;
        private String address;
        private String user_id;
        private String is_del;
        private String user_name;

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

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
