package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class DestinationDetail {


    /**
     * code : 1
     * message : 获取成功
     * data : {"travel":{"id":"12","title":"测试景点2","content":"策划师而已 ","play_way":"露营,购物,徒步","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"5","score":"","province":"26","city":"386","address":"西藏自治区自贡市niadhashdoa","longitude":"131","latitude":"312","status":"1","add_time":"1470723007","update_time":"1470796007","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png"},"travel_reply":[{"id":"3","f_id":"12","user_id":"10009","pid":"0","content":"这是一个测试内容","reply_img":"","reply_time":"1471939164","floor":"1","type":"3","status":"1","like_count":"3","is_like":"1","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"4","f_id":"12","user_id":"10009","pid":"3","content":"这是一个测试内容","reply_img":"","reply_time":"1471939205","floor":"2","type":"3","status":"1","like_count":"3","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"nick_name":"我叫王小","content":"这是一个测试内容","floor":"1","reply_img":""}},{"id":"5","f_id":"12","user_id":"10009","pid":"0","content":"","reply_img":"","reply_time":"1472109446","floor":"3","type":"3","status":"1","like_count":"3","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}}],"have_next":{"nextpage":"0"}}
     */

    private int code;
    private String message;
    /**
     * travel : {"id":"12","title":"测试景点2","content":"策划师而已 ","play_way":"露营,购物,徒步","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"5","score":"","province":"26","city":"386","address":"西藏自治区自贡市niadhashdoa","longitude":"131","latitude":"312","status":"1","add_time":"1470723007","update_time":"1470796007","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png"}
     * travel_reply : [{"id":"3","f_id":"12","user_id":"10009","pid":"0","content":"这是一个测试内容","reply_img":"","reply_time":"1471939164","floor":"1","type":"3","status":"1","like_count":"3","is_like":"1","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"4","f_id":"12","user_id":"10009","pid":"3","content":"这是一个测试内容","reply_img":"","reply_time":"1471939205","floor":"2","type":"3","status":"1","like_count":"3","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"nick_name":"我叫王小","content":"这是一个测试内容","floor":"1","reply_img":""}},{"id":"5","f_id":"12","user_id":"10009","pid":"0","content":"","reply_img":"","reply_time":"1472109446","floor":"3","type":"3","status":"1","like_count":"3","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}}]
     * have_next : {"nextpage":"0"}
     */

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
        /**
         * id : 12
         * title : 测试景点2
         * content : 策划师而已
         * play_way : 露营,购物,徒步
         * picture_id : 10
         * logo_img : /Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg
         * star : 5
         * score :
         * province : 26
         * city : 386
         * address : 西藏自治区自贡市niadhashdoa
         * longitude : 131
         * latitude : 312
         * status : 1
         * add_time : 1470723007
         * update_time : 1470796007
         * travel_img : http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png
         */

        private TravelBean travel;
        /**
         * nextpage : 0
         */

        private HaveNextBean have_next;
        /**
         * id : 3
         * f_id : 12
         * user_id : 10009
         * pid : 0
         * content : 这是一个测试内容
         * reply_img :
         * reply_time : 1471939164
         * floor : 1
         * type : 3
         * status : 1
         * like_count : 3
         * is_like : 1
         * nick_name : 我叫王小
         * user_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png
         * reply : {"floor":"","nick_name":"","content":"","reply_img":""}
         */

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
