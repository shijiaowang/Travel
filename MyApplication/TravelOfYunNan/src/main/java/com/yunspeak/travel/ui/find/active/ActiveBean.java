package com.yunspeak.travel.ui.find.active;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by wangtyang on 2016/7/12 0012.
 */
public class ActiveBean implements ParentBean{


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
