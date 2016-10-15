package com.example.administrator.travel.ui.find;

import java.util.List;

/**
 * Created by wangyang on 2016/10/15 0015.
 */

public class FindBean {

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

        private List<RecommendBean> hot;

        private List<RecommendBean> recommend;

        private List<RecommendBean> banner;

        public List<RecommendBean> getHot() {
            return hot;
        }

        public void setHot(List<RecommendBean> hot) {
            this.hot = hot;
        }

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public List<RecommendBean> getBanner() {
            return banner;
        }

        public void setBanner(List<RecommendBean> banner) {
            this.banner = banner;
        }
        public static class RecommendBean {
            private String id;
            private String logo_img;
            private String title;
            private String province;
            private String city;
            private String address;
            private String is_hot;
            private String is_recommend;
            private String is_banner;
            private int type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getIs_banner() {
                return is_banner;
            }

            public void setIs_banner(String is_banner) {
                this.is_banner = is_banner;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
