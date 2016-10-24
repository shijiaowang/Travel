package com.yunspeak.travel.ui.appoint.settingtitle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SettingTitleBean implements Serializable{
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

        private List<UserLabelBean> user_label;
        private List<UserLabelBean> platform_label;
        private List<UserLabelBean> play_way;

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }

        public List<UserLabelBean> getPlatform_label() {
            return platform_label;
        }

        public void setPlatform_label(List<UserLabelBean> platform_label) {
            this.platform_label = platform_label;
        }

        public List<UserLabelBean> getPlay_way() {
            return play_way;
        }

        public void setPlay_way(List<UserLabelBean> play_way) {
            this.play_way = play_way;
        }





    }
}
