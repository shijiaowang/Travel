package com.example.administrator.travel.utils;


import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

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
}
