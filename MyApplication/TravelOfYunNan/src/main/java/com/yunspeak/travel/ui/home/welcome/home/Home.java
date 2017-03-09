package com.yunspeak.travel.ui.home.welcome.home;

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
        private ActivityBean activit;
        private List<BannerBean> banner;
        private List<IndexTextBean> index_text;
        public ActivityBean getActivit() {
            return activit;
        }
        public void setActivit(ActivityBean activit) {
            this.activit = activit;
        }
        public List<BannerBean> getBanner() {
            return banner;
        }
        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }
        public List<IndexTextBean> getIndex_text() {
            return index_text;
        }
        public void setIndex_text(List<IndexTextBean> index_text) {
            this.index_text = index_text;
        }
    }

    @Override
    public String toString() {
        return "Home{" +
                "data=" + data +
                '}';
    }
}
