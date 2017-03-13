package com.yunspeak.travel.ui.home.welcome.home.model;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2017/3/8.
 */

public class Home extends TravelsObject{
    private DataBean data;
    public DataBean getData() {
        return data;
    }
    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ActivityModel activit;
        private List<BannerModel> banner;
        private List<IndexTextModel> index_text;
        public ActivityModel getActivit() {
            return activit;
        }
        public void setActivit(ActivityModel activit) {
            this.activit = activit;
        }
        public List<BannerModel> getBanner() {
            return banner;
        }
        public void setBanner(List<BannerModel> banner) {
            this.banner = banner;
        }
        public List<IndexTextModel> getIndex_text() {
            return index_text;
        }
        public void setIndex_text(List<IndexTextModel> index_text) {
            this.index_text = index_text;
        }
    }
}
