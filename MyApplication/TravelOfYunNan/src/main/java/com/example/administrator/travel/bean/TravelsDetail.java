package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 * 游记详情
 */
public class TravelsDetail {

    /**
     * code : 1
     * message : 获取成功
     * data : {"travel":{"id":"2","title":"游记测试","content":"这只是一个测试而已daslkdhakshdfklashgdilahdi","author":"你才","logo_img":"/Uploads/Picture/2016-08-15/57b17f9d00af6.png","title_img":"/Uploads/Picture/2016-08-16/57b287bb7617e.png","travel_id":"4","add_time":"1472433810","status":"1","browse":"10","travels_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b287be47b4e.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889114ece.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889439ca6.png","travel_way":"牛逼山-测试1-牛逼山"},"travel_routes":{"star_time":"1472140800","end_time":"1472400000","meet_address":"大苏打","total_price":"471.00","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","count":2,"user":[{"user_img":"http://192.168.1.38/Uploads/0.png","id":"10000","nick_name":"yuns客服"},{"user_img":"http://192.168.1.38/Uploads/1.png","id":"10009","nick_name":"我叫王小"}],"routes":[{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472140800"},{"id":"2","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"测试1","time":"1472140800"},{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472227200"}]},"travel_reply":[{"id":"52","f_id":"2","user_id":"10009","pid":"0","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117758","floor":"9","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"53","f_id":"2","user_id":"10009","pid":"52","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117785","floor":"10","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"9","reply_img":""}},{"id":"54","f_id":"2","user_id":"10009","pid":"53","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117801","floor":"11","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"10","reply_img":""}},{"id":"56","f_id":"2","user_id":"10009","pid":"53","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"13","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"10","reply_img":""}}],"have_next":{"nextpage":"0"}}
     */

    private int code;
    private String message;
    /**
     * travel : {"id":"2","title":"游记测试","content":"这只是一个测试而已daslkdhakshdfklashgdilahdi","author":"你才","logo_img":"/Uploads/Picture/2016-08-15/57b17f9d00af6.png","title_img":"/Uploads/Picture/2016-08-16/57b287bb7617e.png","travel_id":"4","add_time":"1472433810","status":"1","browse":"10","travels_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b287be47b4e.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889114ece.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889439ca6.png","travel_way":"牛逼山-测试1-牛逼山"}
     * travel_routes : {"star_time":"1472140800","end_time":"1472400000","meet_address":"大苏打","total_price":"471.00","travel_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg","count":2,"user":[{"user_img":"http://192.168.1.38/Uploads/0.png","id":"10000","nick_name":"yuns客服"},{"user_img":"http://192.168.1.38/Uploads/1.png","id":"10009","nick_name":"我叫王小"}],"routes":[{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472140800"},{"id":"2","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"测试1","time":"1472140800"},{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472227200"}]}
     * travel_reply : [{"id":"52","f_id":"2","user_id":"10009","pid":"0","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117758","floor":"9","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}},{"id":"53","f_id":"2","user_id":"10009","pid":"52","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117785","floor":"10","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"9","reply_img":""}},{"id":"54","f_id":"2","user_id":"10009","pid":"53","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472117801","floor":"11","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"10","reply_img":""}},{"id":"56","f_id":"2","user_id":"10009","pid":"53","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472174808","floor":"13","type":"1","status":"1","like_count":"1","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","reply":{"nick_name":"我叫王小","content":"这只是一个测试评论而已，而已","floor":"10","reply_img":""}}]
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
         * title : 游记测试
         * content : 这只是一个测试而已daslkdhakshdfklashgdilahdi
         * author : 你才
         * logo_img : /Uploads/Picture/2016-08-15/57b17f9d00af6.png
         * title_img : /Uploads/Picture/2016-08-16/57b287bb7617e.png
         * travel_id : 4
         * add_time : 1472433810
         * status : 1
         * browse : 10
         * travels_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b287be47b4e.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889114ece.png,http://192.168.1.38/Uploads/Picture/2016-08-16/57b2889439ca6.png
         * travel_way : 牛逼山-测试1-牛逼山
         */

        private TravelBean travel;
        /**
         * star_time : 1472140800
         * end_time : 1472400000
         * meet_address : 大苏打
         * total_price : 471.00
         * travel_img : http://192.168.1.38/Uploads/Picture/2016-07-27/579859e0d84f6.jpg
         * count : 2
         * user : [{"user_img":"http://192.168.1.38/Uploads/0.png","id":"10000","nick_name":"yuns客服"},{"user_img":"http://192.168.1.38/Uploads/1.png","id":"10009","nick_name":"我叫王小"}]
         * routes : [{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472140800"},{"id":"2","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"测试1","time":"1472140800"},{"id":"1","logo_img":"http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg","title":"牛逼山","time":"1472227200"}]
         */

        private TravelRoutesBean travel_routes;
        /**
         * nextpage : 0
         */

        private HaveNextBean have_next;
        /**
         * id : 52
         * f_id : 2
         * user_id : 10009
         * pid : 0
         * content : 这只是一个测试评论而已，而已
         * reply_img :
         * reply_time : 1472117758
         * floor : 9
         * type : 1
         * status : 1
         * like_count : 1
         * is_like : 0
         * nick_name : 我叫王小
         * user_img : http://192.168.1.38/Uploads/1.png
         * reply : {"floor":"","nick_name":"","content":"","reply_img":""}
         */

        private List<TravelReplyBean> travel_reply;

        public TravelBean getTravel() {
            return travel;
        }

        public void setTravel(TravelBean travel) {
            this.travel = travel;
        }

        public TravelRoutesBean getTravel_routes() {
            return travel_routes;
        }

        public void setTravel_routes(TravelRoutesBean travel_routes) {
            this.travel_routes = travel_routes;
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
            private String author;
            private String logo_img;
            private String title_img;
            private String travel_id;
            private String add_time;
            private String status;
            private String browse;
            private String travels_img;
            private String travel_way;

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

            public String getTravel_id() {
                return travel_id;
            }

            public void setTravel_id(String travel_id) {
                this.travel_id = travel_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getBrowse() {
                return browse;
            }

            public void setBrowse(String browse) {
                this.browse = browse;
            }

            public String getTravels_img() {
                return travels_img;
            }

            public void setTravels_img(String travels_img) {
                this.travels_img = travels_img;
            }

            public String getTravel_way() {
                return travel_way;
            }

            public void setTravel_way(String travel_way) {
                this.travel_way = travel_way;
            }
        }

        public static class TravelRoutesBean {
            private String star_time;
            private String end_time;
            private String meet_address;
            private String total_price;
            private String travel_img;
            private int count;
            /**
             * user_img : http://192.168.1.38/Uploads/0.png
             * id : 10000
             * nick_name : yuns客服
             */

            private List<UserBean> user;
            /**
             * id : 1
             * logo_img : http://192.168.1.38/Uploads/Picture/2016-07-27/57981a9d1ccf6.jpg
             * title : 牛逼山
             * time : 1472140800
             */

            private List<RoutesBean> routes;

            public String getStar_time() {
                return star_time;
            }

            public void setStar_time(String star_time) {
                this.star_time = star_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getMeet_address() {
                return meet_address;
            }

            public void setMeet_address(String meet_address) {
                this.meet_address = meet_address;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            public String getTravel_img() {
                return travel_img;
            }

            public void setTravel_img(String travel_img) {
                this.travel_img = travel_img;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<UserBean> getUser() {
                return user;
            }

            public void setUser(List<UserBean> user) {
                this.user = user;
            }

            public List<RoutesBean> getRoutes() {
                return routes;
            }

            public void setRoutes(List<RoutesBean> routes) {
                this.routes = routes;
            }

            public static class UserBean {
                private String user_img;
                private String id;
                private String nick_name;

                public String getUser_img() {
                    return user_img;
                }

                public void setUser_img(String user_img) {
                    this.user_img = user_img;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }
            }

            public static class RoutesBean {
                private String id;
                private String logo_img;
                private String title;
                private String time;

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

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
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
