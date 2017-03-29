package com.yunspeak.travel.ui.me.mytheme.model;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.bean.InformBean;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.TravelsActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.ShowImageUtils;

import java.util.List;

/**
 * Created by wangyang on 2017/3/21.
 * 发表
 */

public class PublishModel {
    private String f_id;
    private String content;
    private String add_time;
    private String type;
    private String title;
    private String img;
    private String id;
    private String cname;
    private String count_like;
    private int find_type;
    private int r_id;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<InformBean> inform;

    public List<InformBean> getInform() {
        return inform;
    }

    public void setInform(List<InformBean> inform) {
        this.inform = inform;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private int cid;


    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCount_like() {
        return count_like;
    }

    public void setCount_like(String count_like) {
        this.count_like = count_like;
    }

    public int getFind_type() {
        return find_type;
    }

    public void setFind_type(int find_type) {
        this.find_type = find_type;
    }
    @BindingAdapter("icon_1")
     public static void setPublishImg(ImageView img,String url){
         ShowImageUtils.showCircle(img, R.drawable.boy,url,1);
     }
    @BindingAdapter("publish_parse_text")
    public static void setPublishParseText(TextView textView,String text){
        AiteUtils.parseTextMessage(textView,null,text,false);
    }
    @BindingAdapter({"publish_parse_content","publish_content_inform","publish_url"})
    public static void setPublishContent(TextView textView,String text,List<InformBean> inform,String url){
        textView.setText(AiteUtils.getSmiedTextWithAiteAndLinke(textView.getContext(),text,inform,url));
    }
    @BindingAdapter("publish_time")
    public static void setPublishTime(TextView textView,String time){
        textView.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", time));
    }
    public void onRootClick(View view){
        Context mContext=view.getContext();
        if (type.equals("1")){//帖子
            PostActivity.start(mContext,getId());
        }else {//发现
            switch (find_type){
                case 1://游记
                    TravelsDetailActivity.start(mContext,getId(),getTitle());
                    break;
                case 2://美食
                    DeliciousDetailActivity.start(mContext,getId(),getTitle());
                    break;
                case 3://目的地
                    DestinationDetailActivity.start(mContext,getId(),getTitle());
                    break;
            }
        }
    }
    public void onNameClick(View view) {
        Context mContext=view.getContext();
        if (type.equals("1")) {//帖子
            CircleDetailActivity.start(mContext, String.valueOf(cid));
        } else {//发现
            switch (find_type) {
                case 1://游记
                    Intent intent = new Intent(mContext, TravelsActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 2://美食
                    FindCommonActivity.start(mContext, FindCommonActivity.DELICIOUS_NORMAL, 0);
                    break;
                case 3://目的地
                    FindCommonActivity.start(mContext, FindCommonActivity.DESTINATION_NORMAL, 0);
                    break;
            }
        }

    }

}
