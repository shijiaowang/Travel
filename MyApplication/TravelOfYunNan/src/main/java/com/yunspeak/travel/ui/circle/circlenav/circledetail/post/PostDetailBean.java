package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import com.yunspeak.travel.global.ParentBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/23 0023.
 */
public class PostDetailBean implements ParentBean{


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
        private ForumBean forum;
        private String img_lists;

        private List<ForumReplyBean> forum_reply;

        public ForumBean getForum() {
            return forum;
        }

        public void setForum(ForumBean forum) {
            this.forum = forum;
        }

        public String getImg_lists() {
            return img_lists;
        }

        public void setImg_lists(String img_lists) {
            this.img_lists = img_lists;
        }

        public List<ForumReplyBean> getForum_reply() {
            return forum_reply;
        }

        public void setForum_reply(List<ForumReplyBean> forum_reply) {
            this.forum_reply = forum_reply;
        }

        public static class ForumBean {
            private String id;
            private String cid;
            private String user_id;
            private String title;
            private String content;
            private String forum_img;
            private String time;
            private String status;
            private String is_hot;
            private String update_time;
            private String is_index;
            private String click;
            private String collect;
            private String reply;
            private String reprinted;
            private String count_like;
            private String nick_name;
            private String user_img;
            private String cname;
            private String is_collect;
            private String is_like;
            private String replay_count;
            private String share_url;
            private List<InformBean> inform;

            private List<LikeBean> like;

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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

            public String getForum_img() {
                return forum_img;
            }

            public void setForum_img(String forum_img) {
                this.forum_img = forum_img;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public String getIs_like() {
                return is_like;
            }

            public void setIs_like(String is_like) {
                this.is_like = is_like;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getIs_index() {
                return is_index;
            }

            public void setIs_index(String is_index) {
                this.is_index = is_index;
            }

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getCollect() {
                return collect;
            }

            public void setCollect(String collect) {
                this.collect = collect;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getReprinted() {
                return reprinted;
            }

            public void setReprinted(String reprinted) {
                this.reprinted = reprinted;
            }

            public String getCount_like() {
                return count_like;
            }

            public void setCount_like(String count_like) {
                this.count_like = count_like;
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

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public String getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(String is_collect) {
                this.is_collect = is_collect;
            }

            public String getReplay_count() {
                return replay_count;
            }

            public void setReplay_count(String replay_count) {
                this.replay_count = replay_count;
            }

            public List<InformBean> getInform() {
                return inform;
            }

            public void setInform(List<InformBean> inform) {
                this.inform = inform;
            }

            public List<LikeBean> getLike() {
                return like;
            }

            public void setLike(List<LikeBean> like) {
                this.like = like;
            }


            public static class LikeBean {
                private String nick_name;
                private String id;

                public String getNick_name() {
                    return nick_name;
                }

                public void setNick_name(String nick_name) {
                    this.nick_name = nick_name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class ForumReplyBean {
            private String id;
            private String forum_id;
            private String user_id;
            private String r_user_id;
            private String pid;
            private String content;
            private String reply_img;
            private String reply_time;
            private String floor;
            private String status;
            private String is_read;
            private String update_time;
            private String cid;
            private String like_count;
            private String is_like;
            private String nick_name;
            private String user_img;
            private String r_nick_name;
            private String r_user_img;
            private List<InformBean> inform;

            public List<InformBean> getInform() {
                return inform;
            }

            public void setInform(List<InformBean> inform) {
                this.inform = inform;
            }

            private ReplyBean reply;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getForum_id() {
                return forum_id;
            }

            public void setForum_id(String forum_id) {
                this.forum_id = forum_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getR_user_id() {
                return r_user_id;
            }

            public void setR_user_id(String r_user_id) {
                this.r_user_id = r_user_id;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_read() {
                return is_read;
            }

            public void setIs_read(String is_read) {
                this.is_read = is_read;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
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

            public String getR_nick_name() {
                return r_nick_name;
            }

            public void setR_nick_name(String r_nick_name) {
                this.r_nick_name = r_nick_name;
            }

            public String getR_user_img() {
                return r_user_img;
            }

            public void setR_user_img(String r_user_img) {
                this.r_user_img = r_user_img;
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
                private List<InformBean> inform;

                public List<InformBean> getInform() {
                    return inform;
                }

                public void setInform(List<InformBean> inform) {
                    this.inform = inform;
                }

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
