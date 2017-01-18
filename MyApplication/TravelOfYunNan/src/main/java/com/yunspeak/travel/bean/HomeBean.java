package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/10/17 0017.
 */

public class HomeBean extends TravelsObject {


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
        private List<ForumBean> forum;
        private List<DestinationBean> destination;
        private List<FindTravelBean> find_travel;
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

        public List<ForumBean> getForum() {
            return forum;
        }

        public void setForum(List<ForumBean> forum) {
            this.forum = forum;
        }

        public List<DestinationBean> getDestination() {
            return destination;
        }

        public void setDestination(List<DestinationBean> destination) {
            this.destination = destination;
        }

        public List<FindTravelBean> getFind_travel() {
            return find_travel;
        }

        public void setFind_travel(List<FindTravelBean> find_travel) {
            this.find_travel = find_travel;
        }

        public List<IndexTextBean> getIndex_text() {
            return index_text;
        }

        public void setIndex_text(List<IndexTextBean> index_text) {
            this.index_text = index_text;
        }

        public static class BannerBean {
            private String id;
            private String path;
            private String article_id;
            private String title;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getArticle_id() {
                return article_id;
            }

            public void setArticle_id(String article_id) {
                this.article_id = article_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ForumBean {
            private String id;
            private String title;
            private String circle_img;
            private String cname;
            private String cid;
            private String index_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCircle_img() {
                return circle_img;
            }

            public void setCircle_img(String circle_img) {
                this.circle_img = circle_img;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getIndex_img() {
                return index_img;
            }

            public void setIndex_img(String index_img) {
                this.index_img = index_img;
            }
        }

        public static class DestinationBean {
            private String id;
            private String title;
            private String province;
            private String city;
            private String address;
            private String logo_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }
        }

        public static class FindTravelBean {
            private String id;
            private String title;
            private String author;
            private String logo_img;
            private String title_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }
        }

        public static class IndexTextBean {
            private int type;
            private String title;
            private String img;
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getType() {
                return type;

            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
