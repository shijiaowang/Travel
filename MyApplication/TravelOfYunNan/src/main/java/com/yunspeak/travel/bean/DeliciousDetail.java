package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDetail {


    /**
     * code : 1
     * message : 获取成功
     * data : {"travel":{"id":"2","title":"测试1","content":"测试","play_way":"2","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"3","score":"","province":"24","city":"417","address":"贵州省曲靖21312阿斯大声大气","longitude":"12\u201936","latitude":"12.36","status":"1","add_time":"1470301015","update_time":"1470301015","food_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9f2c6f6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg"},"travel_reply":[{"id":"57","f_id":"2","user_id":"10009","pid":"0","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"14","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":""},{"id":"58","f_id":"2","user_id":"10009","pid":"57","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"15","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"14","reply_img":""}},{"id":"59","f_id":"2","user_id":"10009","pid":"57","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"16","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"14","reply_img":""}},{"id":"60","f_id":"2","user_id":"10009","pid":"58","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174887","floor":"17","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"15","reply_img":""}}],"have_next":{"nextpage":"0"}}
     */

    private int code;
    private String message;
    /**
     * travel : {"id":"2","title":"测试1","content":"测试","play_way":"2","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"3","score":"","province":"24","city":"417","address":"贵州省曲靖21312阿斯大声大气","longitude":"12\u201936","latitude":"12.36","status":"1","add_time":"1470301015","update_time":"1470301015","food_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9f2c6f6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg"}
     * travel_reply : [{"id":"57","f_id":"2","user_id":"10009","pid":"0","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"14","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":""},{"id":"58","f_id":"2","user_id":"10009","pid":"57","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"15","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"14","reply_img":""}},{"id":"59","f_id":"2","user_id":"10009","pid":"57","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"16","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"14","reply_img":""}},{"id":"60","f_id":"2","user_id":"10009","pid":"58","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174887","floor":"17","type":"2","status":"1","like_count":"0","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"15","reply_img":""}}]
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
         * id : 2
         * title : 测试1
         * content : 测试
         * play_way : 2
         * picture_id : 10
         * logo_img : /Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg
         * star : 3
         * score :
         * province : 24
         * city : 417
         * address : 贵州省曲靖21312阿斯大声大气
         * longitude : 12’36
         * latitude : 12.36
         * status : 1
         * add_time : 1470301015
         * update_time : 1470301015
         * food_img : http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9f2c6f6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg
         */

        private TravelBean travel;
        /**
         * nextpage : 0
         */

        private HaveNextBean have_next;
        /**
         * id : 57
         * f_id : 2
         * user_id : 10009
         * pid : 0
         * content : 这只是一个测试评论而已，而已
         * reply_img :
         * reply_time : 1472174808
         * floor : 14
         * type : 2
         * status : 1
         * like_count : 0
         * is_like : 0
         * nick_name : 我叫王小
         * user_img : http://192.168.1.38/Uploads/1.png
         * reply :
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
            private String food_img;

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

            public String getFood_img() {
                return food_img;
            }

            public void setFood_img(String food_img) {
                this.food_img = food_img;
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
