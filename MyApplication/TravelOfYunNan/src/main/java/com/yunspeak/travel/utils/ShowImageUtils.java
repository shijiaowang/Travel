package com.yunspeak.travel.utils;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunspeak.travel.glide.GlideCircleTransform;

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
    public static void showCircle(ImageView imageView,int normalRes, String url,int borderWidth){
        if (imageView==null)return;
        if (TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(normalRes).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(url).centerCrop().placeholder(normalRes).error(normalRes).transform(new GlideCircleTransform(imageView.getContext(),borderWidth, Color.WHITE)).into(imageView);
        }
    }
    public static void showCircle(ImageView imageView,int normalRes, String url){
        showCircle(imageView,normalRes,url,0);
    }
}
