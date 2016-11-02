package com.yunspeak.travel.utils;

import android.net.Uri;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.yunspeak.travel.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/10/8.
 */

public class FrescoUtils {
    /**
     * 加载圆形头像
     * @param simpleDraweeView 显示
     */
    public static void displayIcon(SimpleDraweeView simpleDraweeView, String url){
        if (simpleDraweeView==null || url==null)return;
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setBorder(UIUtils.getColor(R.color.colorFAFAFA),2);
        roundingParams.setRoundAsCircle(true);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(50,50))
                .build();
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                //构建
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        //获取GenericDraweeHierarchy对象
        simpleDraweeView.setController(controller);
    }
    /**
     * 加载圆角icon
     * @param simpleDraweeView 显示
     */
    public static void displayRoundIcon(SimpleDraweeView simpleDraweeView, String url){
        if (simpleDraweeView==null || url==null)return;
        Uri uri=Uri.parse(url);
        if (simpleDraweeView==null || uri==null)return;
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setCornersRadius(10f);
        //获取GenericDraweeHierarchy对象
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(50,50))
                .build();
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                //构建
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }
    /**
     * 加载一般图片
     * @param simpleDraweeView 显示
     */
    public static void displayNormal(SimpleDraweeView simpleDraweeView, String url,int width,int height,int failimge){
        if (simpleDraweeView==null || url==null)return;
        Uri uri=Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
               .setResizeOptions(new ResizeOptions(width,height))
                .build();
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(UIUtils.getContext().getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setFailureImage(failimge, ScalingUtils.ScaleType.CENTER_INSIDE)
                .setPlaceholderImage(failimge, ScalingUtils.ScaleType.CENTER_INSIDE)
                //构建
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setController(controller);
    }
    /**
     * 加载一般图片
     * @param simpleDraweeView 显示
     */
    public static void displayNormal(SimpleDraweeView simpleDraweeView, String url){
       displayNormal(simpleDraweeView,url,200,200,R.drawable.activity_circle_bg);
    }
    /**
     * 加载一般图片
     * @param simpleDraweeView 显示
     */
    public static void displayNormal(SimpleDraweeView simpleDraweeView, String url,int width,int height){
        displayNormal(simpleDraweeView,url,width,height,R.drawable.activity_circle_bg);
    }
    /**
     * 加载一般图片
     * @param simpleDraweeView 显示
     */
    public static void displayNormal(SimpleDraweeView simpleDraweeView, String url,int res){
        displayNormal(simpleDraweeView,url,200,200,res);
    }


}
