package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/24 0024.
 */
public class DestinationDetailBean extends TravelsObject implements ParentBean {


    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private TravelBean travel;

        private HaveNextBean have_next;
        private List<TravelReplyBean> travel_reply;

        public TravelBean getTravel() {
            return travel;
        }

        public void setTravel(TravelBean travel) {
            this.travel = travel;
        }

        public HaveNextBean getHave_next() {
            return have_next;
        }

        public void setHave_next(HaveNextBean have_next) {
            this.have_next = have_next;
        }

        public List<TravelReplyBean> getTravel_reply() {
            return travel_reply;
        }

        public void setTravel_reply(List<TravelReplyBean> travel_reply) {
            this.travel_reply = travel_reply;
        }

        public static class TravelBean {
            private String id;
            private String title;
            private String content;
            private String play_way;
            private String picture_id;
            private String logo_img;
            private String star;
            private String score;
            private String province;
            private String city;
            private String address;
            private String longitude;
            private String latitude;
            private String status;
            private String add_time;
            private String update_time;
            private String travel_img;
            private String is_collect;
            private String share_url;

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public String getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(String is_collect) {
                this.is_collect = is_collect;
            }

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPlay_way() {
                return play_way;
            }

            public void setPlay_way(String play_way) {
                this.play_way = play_way;
            }

            public String getPicture_id() {
                return picture_id;
            }

            public void setPicture_id(String picture_id) {
                this.picture_id = picture_id;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
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

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getTravel_img() {
                return travel_img;
            }

            public void setTravel_img(String travel_img) {
                this.travel_img = travel_img;
            }
        }

        public static class HaveNextBean {
            private String nextpage;

            public String getNextpage() {
                return nextpage;
            }

            public void setNextpage(String nextpage) {
                this.nextpage = nextpage;
            }
        }


    }
}
