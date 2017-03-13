package com.yunspeak.travel.ui.home.welcome.home.model;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.ui.home.welcome.splash.register.WebViewActivity;
import com.yunspeak.travel.utils.ShowImageUtils;
import com.yunspeak.travel.utils.StringUtils;


/**
 * Created by wangyang on 2017/3/9.
 * 首页 banner 轮播
 */

public class BannerModel {
    private String id;
    private String path;
    private String article_id;
    private String title;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        ShowImageUtils.showNormal(imageView, R.drawable.normal_2_1, imageUrl);
    }

    @CheckNetwork
    public void onClick(View view) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url)) {
            return;
        }
        WebViewActivity.start(view.getContext(), title, url);
    }
}