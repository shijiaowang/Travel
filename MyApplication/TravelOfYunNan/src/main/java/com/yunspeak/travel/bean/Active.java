package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by wangtyang on 2016/7/12 0012.
 */
public class Active {

    /**
     * code : 1
     * message : 加载成功
     * data : [{"id":"1","title":"活动测试","activity_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png","now_people":"2","type":"1"},{"id":"2","title":"测试活动哦","activity_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png","now_people":"0","type":"2"},{"id":"3","title":"车市标题","activity_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png","now_people":"0","type":"1"}]
     */

    private int code;
    private String message;
    /**
     * id : 1
     * title : 活动测试
     * activity_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b27d441569e.png
     * now_people : 2
     * type : 1
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
        private String activity_img;
        private String now_people;
        private String type;

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

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getNow_people() {
            return now_people;
        }

        public void setNow_people(String now_people) {
            this.now_people = now_people;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
