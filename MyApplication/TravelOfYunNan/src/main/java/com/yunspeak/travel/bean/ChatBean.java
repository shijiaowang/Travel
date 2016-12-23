package com.yunspeak.travel.bean;


import com.hyphenate.easeui.domain.UserInfo;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/20 0020.
 */

public class ChatBean extends TravelsObject {
    private List<UserInfo> data;
    private TravelBean travel;
    public List<UserInfo> getData() {
        return data;
    }
    public void setData(List<UserInfo> data) {
        this.data = data;
    }
    public TravelBean getTravel() {
        return travel;
    }
    public void setTravel(TravelBean travel) {
        this.travel = travel;
    }
    public static class TravelBean {
        private String travel_img;
        private String travel_title;
        public String getTravel_img() {
            return travel_img;
        }
        public void setTravel_img(String travel_img) {
            this.travel_img = travel_img;
        }
        public String getTravel_title() {
            return travel_title;
        }
        public void setTravel_title(String travel_title) {
            this.travel_title = travel_title;
        }
    }

}