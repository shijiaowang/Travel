package com.yunspeak.travel.ui.find.travels.travelsdetail;

import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/29 0029.
 * 游记详情
 */
public class TravelsDetailBean implements ParentBean{


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

        private TravelBean travel;


        private TravelRoutesBean travel_routes;
        /**
         * nextpage : 1
         */

        private HaveNextBean have_next;

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
            private String author;
            private String logo_img;
            private String title_img;
            private String travel_id;
            private String add_time;
            private String status;
            private String browse;
            private String travels_img;
            private String url;
            private String travel_way;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            private List<UserBean> user;


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
