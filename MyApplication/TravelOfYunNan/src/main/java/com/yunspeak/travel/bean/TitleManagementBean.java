package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;


import java.util.List;

/**
 * Created by wangyang on 2016/9/27 0027.
 */

public class TitleManagementBean extends TravelsObject {
    private DataBean data;
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<LabelTitleBean> label_title;

        private List<UserLabelBean> user_label;
        private List<List<OfficialLabelBean>> official_label;

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

        public List<List<OfficialLabelBean>> getOfficial_label() {
            return official_label;
        }

        public void setOfficial_label(List<List<OfficialLabelBean>> official_label) {
            this.official_label = official_label;
        }


    }

}
