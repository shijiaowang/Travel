package com.example.administrator.travel.utils;

import android.net.Uri;

import com.example.administrator.travel.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by wangyang on 2016/10/8.
 */

public class FrescoUtils {
    /**
     * 加载圆形头像
     * @param simpleDraweeView 显示
     * @param uri 地址
     */
    public static void displayIcon(SimpleDraweeView simpleDraweeView, Uri uri){
        if (simpleDraweeView==null || uri==null)return;
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setBorder(UIUtils.getColor(R.color.colorFAFAFA),2);
        roundingParams.setRoundAsCircle(true);
        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                //构建
                .build();

        //构建Controller
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //设置需要下载的图片地址
                .setUri(uri)
                //构建
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }
    /**
     * 加载圆角icon
     * @param simpleDraweeView 显示
     * @param uri 地址
     */
    public static void displayRoundIcon(SimpleDraweeView simpleDraweeView, Uri uri){
        if (simpleDraweeView==null || uri==null)return;
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setCornersRadius(10f);
        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                //构建
                .build();

        //构建Controller
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //设置需要下载的图片地址
                .setUri(uri)
                //构建
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }
    /**
     * 加载一般图片
     * @param simpleDraweeView 显示
     * @param uri 地址
     */
    public static void displayNormal(SimpleDraweeView simpleDraweeView, Uri uri){
        if (simpleDraweeView==null || uri==null)return;
        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(3000)
                //构建
                .build();
        simpleDraweeView.setHierarchy(hierarchy);

        //构建Controller
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //设置需要下载的图片地址
                .setUri(uri)
                //构建
                .build();
        simpleDraweeView.setController(controller);

    }
}
