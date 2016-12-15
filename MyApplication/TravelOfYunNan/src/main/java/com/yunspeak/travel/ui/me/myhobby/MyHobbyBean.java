package com.yunspeak.travel.ui.me.myhobby;


import java.util.List;

/**
 * Created by wangyang on 2016/9/28 0028.
 */

public class MyHobbyBean {

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


        private List<LabelTitleBean> label_title;

        private List<UserLabelBean> user_label;


        private List<List<UserLabelBean>> official_label;

        public List<LabelTitleBean> getLabel_title() {
            return label_title;
        }

        public void setLabel_title(List<LabelTitleBean> label_title) {
            this.label_title = label_title;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

        public List<List<UserLabelBean>> getOfficial_label() {
            return official_label;
        }

        public void setOfficial_label(List<List<UserLabelBean>> official_label) {
            this.official_label = official_label;
        }

    }
}
