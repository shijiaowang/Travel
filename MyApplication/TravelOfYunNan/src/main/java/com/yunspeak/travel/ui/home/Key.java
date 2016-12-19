package com.yunspeak.travel.ui.home;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class Key extends TravelsObject {





    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
