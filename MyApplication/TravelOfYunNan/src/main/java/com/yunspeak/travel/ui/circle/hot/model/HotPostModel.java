package com.yunspeak.travel.ui.circle.hot.model;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.InformBean;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.ShowImageUtils;

import java.util.List;

/**
 * Created by wangyang on 2017/3/16.
 * 热帖
 */

public class HotPostModel {
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
    private String count_reply;

    private List<InformBean> inform;

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

    public String getCount_reply() {
        return count_reply;
    }

    public void setCount_reply(String count_reply) {
        this.count_reply = count_reply;
    }
    public List<InformBean> getInform() {
        return inform;
    }
    public void setInform(List<InformBean> inform) {
        this.inform = inform;
    }
    @BindingAdapter("bind:img")
    public static void loadImg(ImageView imageView,String url){
       ShowImageUtils.showCircle(imageView, R.drawable.boy,url,2);
   }
    @BindingAdapter("bind:parse_text")
    public static void parseText(TextView textView, String title){
        if (TextUtils.isEmpty(title)){
            return;
        }
        AiteUtils.parseTextMessage(textView, null, title, textView.getContext(),false);
    }
}
