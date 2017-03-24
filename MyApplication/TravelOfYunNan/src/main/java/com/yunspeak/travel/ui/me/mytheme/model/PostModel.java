package com.yunspeak.travel.ui.me.mytheme.model;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.InformBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.ShowImageUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;

/**
 * Created by wangyang on 2017/3/24.
 */

public class PostModel {
        private String id;
        private String cid;
        private String user_id;
        private String title;
        private String content;
        private String add_time;
        private String cname;
        private String img;
        private String count_reply;
        private String count_like;
        private List<InformBean> inform;

        public List<InformBean> getInform() {
            return inform;
        }

        public void setInform(List<InformBean> inform) {
            this.inform = inform;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCount_reply() {
            return count_reply;
        }

        public void setCount_reply(String count_reply) {
            this.count_reply = count_reply;
        }

        public String getCount_like() {
            return count_like;
        }

        public void setCount_like(String count_like) {
            this.count_like = count_like;
        }
      @BindingAdapter("setPostImg")
      public static void setPostImg(ImageView img,String url){
          ShowImageUtils.showCircle(img, R.drawable.boy,url,1);
      }
    @BindingAdapter("postParseText")
    public static void setPostParseText(TextView textView, String text){
        AiteUtils.parseTextMessage(textView,null,text,false);
    }
    @BindingAdapter({"postParseContent","postParseInform"})
    public static void setPostContent(TextView textView,String text,List<InformBean> inform){
        AiteUtils.parseTextMessage(textView,inform,text,false);
    }
    @BindingAdapter("postParseTime")
    public static void setPostTime(TextView textView,String time){
        textView.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", time));
    }
    public void onCnameClick(View view){
        CircleDetailActivity.start(view.getContext(),getCid());
    }
    public void onItemClick(View view){
        PostActivity.start(view.getContext(),getId());
    }
}
