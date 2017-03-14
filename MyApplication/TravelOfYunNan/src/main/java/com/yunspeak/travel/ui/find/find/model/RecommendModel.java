package com.yunspeak.travel.ui.find.find.model;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.ui.find.find.FindClickListener;
import com.yunspeak.travel.utils.ShowImageUtils;

/**
 * Created by wangyang on 2017/3/13.
 * 发现 通用model
 */

public class RecommendModel {
    private String id;
    private String logo_img;
    private String title;
    private String province;
    private String city;
    private String address;
    private String is_hot;
    private String is_recommend;
    private String is_banner;
    private int type;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getIs_banner() {
        return is_banner;
    }

    public void setIs_banner(String is_banner) {
        this.is_banner = is_banner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    @BindingAdapter("bind:img")
     public static void loadImage(ImageView imageView,String url){
         ShowImageUtils.showNormal(imageView, R.drawable.normal_2_1,url);
     }
    @CheckNetwork
    public void onClick(View view){
        FindClickListener.onClick(view,this);
    }
}
