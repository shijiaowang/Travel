package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class DestinationDetail {

    /**
     * code : 1
     * message : 获取成功
     * data : {"travel":{"id":"12","title":"测试景点2","content":"策划师而已 ","play_way":"露营,购物,徒步","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"5","score":"","province":"26","city":"386","address":"西藏自治区自贡市niadhashdoa","longitude":"131","latitude":"312","status":"1","add_time":"1470723007","update_time":"1470796007","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png"},"travel_reply":[{"id":"3","f_id":"12","user_id":"10009","pid":"0","content":"这是一个测试内容","reply_img":"","reply_time":"1471939164","floor":"1","type":"3","status":"1","like_count":"2","is_like":"0","nick_name":"我叫王小","user_img":"","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"4","f_id":"12","user_id":"10009","pid":"3","content":"这是一个测试内容","reply_img":"","reply_time":"1471939205","floor":"2","type":"3","status":"1","like_count":"2","is_like":"0","nick_name":"我叫王小","user_img":"","reply":{"nick_name":"我叫王小","content":"这是一个测试内容","floor":"1","reply_img":""}}]}
     */

    private int code;
    private String message;
    /**
     * travel : {"id":"12","title":"测试景点2","content":"策划师而已 ","play_way":"露营,购物,徒步","picture_id":"10","logo_img":"/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","star":"5","score":"","province":"26","city":"386","address":"西藏自治区自贡市niadhashdoa","longitude":"131","latitude":"312","status":"1","add_time":"1470723007","update_time":"1470796007","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981bd2cc976.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg,http://192.168.1.38/Uploads/Picture/2016-07-27/579859dec9a96.png"}
     * travel_reply : [{"id":"3","f_id":"12","user_id":"10009","pid":"0","content":"这是一个测试内容","reply_img":"","reply_time":"1471939164","floor":"1","type":"3","status":"1","like_count":"2","is_like":"0","nick_name":"我叫王小","user_img":"","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"4","f_id":"12","user_id":"10009","pid":"3","content":"这是一个测试内容","reply_img":"","reply_time":"1471939205","floor":"2","type":"3","status":"1","like_count":"2","is_like":"0","nick_name":"我叫王小","user_img":"","reply":{"nick_name":"我叫王小","content":"这是一个测试内容","floor":"1","reply_img":""}}]
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
         * like_count : 2
         * is_like : 0
         * nick_name : 我叫王小
         * user_img :
         * reply : {"floor":"","nick_name":"","content":"","reply_img":""}
         */

        private List<TravelReplyBean> travel_reply;

        public TravelBean getTravel() {
            return travel;
        }

        public void setTravel(TravelBean travel) {
            this.travel = travel;
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

        public static class TravelReplyBean {
            private String id;
            private String f_id;
            private String user_id;
            private String pid;
            private String content;
            private String reply_img;
            private String reply_time;
            private String floor;
            private String type;
            private String status;
            private String like_count;
            private String is_like;
            private String nick_name;
            private String user_img;
            /**
             * floor :
             * nick_name :
             * content :
             * reply_img :
             */

            private ReplyBean reply;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getF_id() {
                return f_id;
            }

            public void setF_id(String f_id) {
                this.f_id = f_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getReply_img() {
                return reply_img;
            }

            public void setReply_img(String reply_img) {
                this.reply_img = reply_img;
            }

            public String getReply_time() {
                return reply_time;
            }

            public void setReply_time(String reply_time) {
                this.reply_time = reply_time;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getLike_count() {
                return like_count;
            }

            public void setLike_count(String like_count) {
                this.like_count = like_count;
            }

            public String getIs_like() {
                return is_like;
            }

            public void setIs_like(String is_like) {
                this.is_like = is_like;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }

            public ReplyBean getReply() {
                return reply;
            }

            public void setReply(ReplyBean reply) {
                this.reply = reply;
            }

            public static class ReplyBean {
                private String floor;
                private String nick_name;
                private String content;
                private String reply_img;

                public String getFloor() {
                    return floor;
                }

                public void setFloor(String floor) {
                    this.floor = floor;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getReply_img() {
                    return reply_img;
                }

                public void setReply_img(String reply_img) {
                    this.reply_img = reply_img;
                }
            }
        }
    }
}
