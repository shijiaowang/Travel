package com.example.administrator.travel.utils;


import android.widget.ImageView;

import com.example.administrator.travel.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;



/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class ImageOptionsUtil {

    private static ImageOptions imageOptions;
    private static ImageOptions userIconImageOptions;

    public static ImageOptions getImageOptions(){
        if (imageOptions==null) {
            imageOptions = new ImageOptions.Builder()
                    .setUseMemCache(true)//使用缓存
                    /*.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                    .setRadius(DensityUtil.dip2px(5))*/
                            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                   /* .setCrop(true) */// 很多时候设置了合适的scaleType也不需要它.
                            // 加载中或错误图片的ScaleType
                            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                   /* .setImageScaleType(ImageView.ScaleType.CENTER_CROP)*/
                   /* .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)*/
                    .build();
        }
        return imageOptions;
    }
    public static ImageOptions getUserIconImageOptions(){
        if (userIconImageOptions==null) {
            userIconImageOptions = new ImageOptions.Builder()
                    .setCircular(true)//使用圆形图片
                    .setUseMemCache(true)//使用缓存
                    .setRadius(DensityUtil.dip2px(5))
                    /*.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))

                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                   /* .setCrop(true) */// 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                   /* .setImageScaleType(ImageView.ScaleType.CENTER_CROP)*/
                   /* .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setFailureDrawableId(R.mipmap.ic_launcher)*/
                    .build();
        }
        return imageOptions;
    }
    public static ImageOptions getBySetSize(int width,int height){
       return new ImageOptions.Builder().setSize(width,height).setCrop(true).build();
    }
    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.fragment_me_icon)
                .setFailureDrawableId(R.drawable.fragment_me_icon)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }

    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     * @param radius    圆角半径，
     */
    public static void display(ImageView imageView, String iconUrl, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(radius))
                .setIgnoreGif(false)
                .setCrop(true)//是否对图片进行裁剪
                .setLoadingDrawableId(R.drawable.fragment_me_icon)
                .setFailureDrawableId(R.drawable.fragment_me_icon)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                .setLoadingDrawableId(R.drawable.fragment_me_icon)
                .setFailureDrawableId(R.drawable.fragment_me_icon)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

}

