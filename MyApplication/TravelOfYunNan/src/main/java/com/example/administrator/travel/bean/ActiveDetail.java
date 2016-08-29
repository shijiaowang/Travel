package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/8/29 0029.
 * 活动详情
 */
public class ActiveDetail {

    /**
     * code : 1
     * message : 加载成功
     * data : {"id":"1","title":"活动测试","content":"活动测试内容","star_time":"1471410000","end_time":"1480435200","activity_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png","title_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png","max_people":"10","now_people":"2","price":"500.00","add_time":"1471340931","type":"1","status":"1","is_into":"0"}
     */

    private int code;
    private String message;
    /**
     * id : 1
     * title : 活动测试
     * content : 活动测试内容
     * star_time : 1471410000
     * end_time : 1480435200
     * activity_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png
     * title_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png
     * max_people : 10
     * now_people : 2
     * price : 500.00
     * add_time : 1471340931
     * type : 1
     * status : 1
     * is_into : 0
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
        private String title;
        private String content;
        private String star_time;
        private String end_time;
        private String activity_img;
        private String title_img;
        private String max_people;
        private String now_people;
        private String price;
        private String add_time;
        private String type;
        private String status;
        private String is_into;

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

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getTitle_img() {
            return title_img;
        }

        public void setTitle_img(String title_img) {
            this.title_img = title_img;
        }

        public String getMax_people() {
            return max_people;
        }

        public void setMax_people(String max_people) {
            this.max_people = max_people;
        }

        public String getNow_people() {
            return now_people;
        }

        public void setNow_people(String now_people) {
            this.now_people = now_people;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_into() {
            return is_into;
        }

        public void setIs_into(String is_into) {
            this.is_into = is_into;
        }
    }
}
