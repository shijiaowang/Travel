package com.yunspeak.travel.utils;

import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yunspeak.travel.R;
import com.yunspeak.travel.glide.GlideCircleTransform;
import com.yunspeak.travel.glide.GlideRoundTransform;
import com.yunspeak.travel.ui.view.CircleImageView;

/**
 * Created by wangyang on 2017/3/13.
 * 图片显示工具类
 */

public class ShowImageUtils {
    /**
     * 展示普通的图片
     * @param imageView 显示图片的view
     * @param normalRes 错误时显示的图片
     * @param url 图片的网络链接
     */
    public static void showNormal(ImageView imageView, int normalRes, String url){
        if (imageView==null)return;
        if (TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(normalRes).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(url).placeholder(normalRes).error(normalRes).into(imageView);
        }
    }
    public static void showCircle(ImageView imageView,String url){
        showCircle(imageView,R.drawable.boy,url,2);
    }
    public static void showCircle(ImageView imageView,int normalRes, String url,int borderWidth){
        if (imageView==null)return;
        if (TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(normalRes).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(url).placeholder(normalRes).error(normalRes).transform(new GlideCircleTransform(imageView.getContext(),borderWidth, Color.WHITE)).into(imageView);
        }
    }
    public static void showCircle(final CircleImageView imageView, int normalRes, String url){
        if (imageView==null)return;
        if (TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(normalRes).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                }
            });
        }else {
            Glide.with(imageView.getContext()).load(url).centerCrop().placeholder(normalRes).error(normalRes).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
    }
    public static void showRound(ImageView imageView,String url){
        showRound(imageView,url,4);
    }
    public static void showRound(ImageView imageView,String url,int rudias){
        if (imageView==null)return;
        if (TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(R.drawable.normal_1_1).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.normal_1_1).error(R.drawable.normal_1_1).transform(new GlideRoundTransform(imageView.getContext(),rudias)).into(imageView);
        }
    }
    public static void showCircle(ImageView imageView,int normalRes, String url){
        showCircle(imageView,normalRes,url,0);
    }
}
