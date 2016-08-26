package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 游记列表
 */
public class Travels {

    /**
     * code : 1
     * message : 加载成功
     * data : [{"id":"2","title":"游记测试","author":"你才","logo_img":"http://192.168.1.38/Uploads/Picture/2016-08-15/57b17f9d00af6.png","title_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b287bb7617e.png","travel_id":"1","add_time":"1471334626","status":"1"},{"id":"1","title":"简单的一个游记而已","author":"王尼玛","logo_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b287be47b4e.png","title_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b287bb7617e.png","travel_id":"0","add_time":"1469602273","status":"1"}]
     */

    private int code;
    private String message;
    /**
     * id : 2
     * title : 游记测试
     * author : 你才
     * logo_img : http://192.168.1.38/Uploads/Picture/2016-08-15/57b17f9d00af6.png
     * title_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b287bb7617e.png
     * travel_id : 1
     * add_time : 1471334626
     * status : 1
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
        private String title;
        private String author;
        private String logo_img;
        private String title_img;
        private String travel_id;
        private String add_time;
        private String status;

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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }

        public String getTitle_img() {
            return title_img;
        }

        public void setTitle_img(String title_img) {
            this.title_img = title_img;
        }

        public String getTravel_id() {
            return travel_id;
        }

        public void setTravel_id(String travel_id) {
            this.travel_id = travel_id;
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
    }
}
