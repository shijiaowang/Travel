package com.yunspeak.travel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyang on 2016/11/14 0014.
 */

public class DynamicBean implements Serializable{
    private String id;
    private String cid;
    private String time;
    private String title;
    private String type;
    private String content;
    private String user_img;
    private String nick_name;
    private String cname;
    private String fid;
    private int f_type;
    private String reply_img;
    private String re_nick_name;
    private String re_user_id;
    private String re_reply_img;
    private String like_count;
    private String is_like;
    private List<InformBean> inform;
    private List<InformBean> re_inform;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getF_type() {
        return f_type;
    }

    public void setF_type(int f_type) {
        this.f_type = f_type;
    }

    public String getReply_img() {
        return reply_img;
    }

    public void setReply_img(String reply_img) {
        this.reply_img = reply_img;
    }

    public String getRe_nick_name() {
        return re_nick_name;
    }

    public void setRe_nick_name(String re_nick_name) {
        this.re_nick_name = re_nick_name;
    }

    public String getRe_user_id() {
        return re_user_id;
    }

    public void setRe_user_id(String re_user_id) {
        this.re_user_id = re_user_id;
    }

    public String getRe_reply_img() {
        return re_reply_img;
    }

    public void setRe_reply_img(String re_reply_img) {
        this.re_reply_img = re_reply_img;
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

    public List<InformBean> getInform() {
        return inform;
    }

    public void setInform(List<InformBean> inform) {
        this.inform = inform;
    }

    public List<InformBean> getRe_inform() {
        return re_inform;
    }

    public void setRe_inform(List<InformBean> re_inform) {
        this.re_inform = re_inform;
    }

}
