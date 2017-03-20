package com.yunspeak.travel.ui.home.welcome.home.model;


import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.ui.home.welcome.homeswitch.HomeSwitchActivity;
import com.yunspeak.travel.utils.ShowImageUtils;


/**
 * Created by wangyang on 2017/3/9.
 * 首页 索引
 */

public  class IndexTextModel{
    private String id;
    private int type;
    private String title;
    private String img;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @BindingAdapter("bind:img")
    public static void loadImage(ImageView imageView,String url){
        ShowImageUtils.showNormal(imageView,R.drawable.normal_2_1,url);
    }
    @CheckNetwork
    public void onClick(View view){
        HomeSwitchActivity.start(view.getContext(),getUrl(),getType());
    }
}