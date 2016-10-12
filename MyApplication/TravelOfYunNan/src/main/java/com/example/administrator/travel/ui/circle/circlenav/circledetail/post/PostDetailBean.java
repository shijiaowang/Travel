package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import java.util.List;

/**
 * Created by wangyang on 2016/8/23 0023.
 */
public class PostDetailBean {

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

        private List<ForumReplyBean> forum_reply;

        public ForumBean getForum() {
            return forum;
        }

        public void setForum(ForumBean forum) {
            this.forum = forum;
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
            private String inform;
            private String forum_img;
            private String time;
            private String status;
            private String is_hot;
            private String update_time;
            private String nick_name;
            private String user_img;
            private String cname;
            private String replay_count;

            public String getReplay_count() {
                return replay_count;
            }

            public void setReplay_count(String replay_count) {
                this.replay_count = replay_count;
            }

            /**
             * nick_name : 我叫王小
             * id : 10009
             */

            private List<LikeBean> like;

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

            public String getInform() {
                return inform;
            }

            public void setInform(String inform) {
                this.inform = inform;
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

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
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
            private String like_count;
            private String is_like;
            private String nick_name;
            private String user_img;
            private String r_nick_name;
            private String r_user_img;

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
