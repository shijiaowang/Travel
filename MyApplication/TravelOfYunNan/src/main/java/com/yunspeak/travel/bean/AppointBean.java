package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.bean.CityBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/7 0007.
 */

public class AppointBean extends TravelsObject {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private List<CityBean> label_user;


        private List<CityBean> label_office;

        private List<CityBean> label_play;

        public List<CityBean> getLabel_user() {
            return label_user;
        }

        public void setLabel_user(List<CityBean> label_user) {
            this.label_user = label_user;
        }

        public List<CityBean> getLabel_office() {
            return label_office;
        }

        public void setLabel_office(List<CityBean> label_office) {
            this.label_office = label_office;
        }

        public List<CityBean> getLabel_play() {
            return label_play;
        }

        public void setLabel_play(List<CityBean> label_play) {
            this.label_play = label_play;
        }

    }
}
