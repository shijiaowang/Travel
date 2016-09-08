package com.example.administrator.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class TopStore {

    /**
     * code : 1
     * message : 成功
     * data : {"forum":{"id":"42","cid":"6","user_id":"10009","title":"还国家主席","content":"咯离开了这个世界","inform":"","forum_img":"http://192.168.1.38/Uploads/Api/Forum/2016-08-30/57c5016f12a0d.jpg","time":"1472528751","status":"1","is_hot":"0","update_time":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","cname":"摄影","like":[],"replay_count":"0"},"forum_reply":[]}
     */

    private int code;
    private String message;
    /**
     * forum : {"id":"42","cid":"6","user_id":"10009","title":"还国家主席","content":"咯离开了这个世界","inform":"","forum_img":"http://192.168.1.38/Uploads/Api/Forum/2016-08-30/57c5016f12a0d.jpg","time":"1472528751","status":"1","is_hot":"0","update_time":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/1.png","cname":"摄影","like":[],"replay_count":"0"}
     * forum_reply : []
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
         * id : 42
         * cid : 6
         * user_id : 10009
         * title : 还国家主席
         * content : 咯离开了这个世界
         * inform :
         * forum_img : http://192.168.1.38/Uploads/Api/Forum/2016-08-30/57c5016f12a0d.jpg
         * time : 1472528751
         * status : 1
         * is_hot : 0
         * update_time : 0
         * nick_name : 我叫王小
         * user_img : http://192.168.1.38/Uploads/1.png
         * cname : 摄影
         * like : []
         * replay_count : 0
         */

        private ForumBean forum;
        private List<?> forum_reply;

        public ForumBean getForum() {
            return forum;
        }

        public void setForum(ForumBean forum) {
            this.forum = forum;
        }

        public List<?> getForum_reply() {
            return forum_reply;
        }

        public void setForum_reply(List<?> forum_reply) {
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
            private List<?> like;

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

            public String getReplay_count() {
                return replay_count;
            }

            public void setReplay_count(String replay_count) {
                this.replay_count = replay_count;
            }

            public List<?> getLike() {
                return like;
            }

            public void setLike(List<?> like) {
                this.like = like;
            }
        }
    }
}
