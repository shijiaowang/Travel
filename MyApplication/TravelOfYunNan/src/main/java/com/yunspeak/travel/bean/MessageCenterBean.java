package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/9/30 0030.
 */

public class MessageCenterBean extends TravelsObject {


    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int travel;
        private int system;
        private int letter;
        private int user;

        public int getTravel() {
            return travel;
        }

        public void setTravel(int travel) {
            this.travel = travel;
        }

        public int getSystem() {
            return system;
        }

        public void setSystem(int system) {
            this.system = system;
        }

        public int getLetter() {
            return letter;
        }

        public void setLetter(int letter) {
            this.letter = letter;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }
    }
}
