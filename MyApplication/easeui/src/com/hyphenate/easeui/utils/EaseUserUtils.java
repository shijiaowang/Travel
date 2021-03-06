package com.hyphenate.easeui.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, SimpleDraweeView imageView) {
        EaseUser userInfo = getUserInfo(username);
        if (userInfo==null){
            imageView.setImageURI(Uri.parse("res:///"+R.drawable.boy));
        }else {
            displayIcon(imageView, userInfo.getAvatar(), context);
        }
    }
    /**
     * set user avatar
     *
     * @param username
     */
    public static void setGroupAvatar(Context context, String username, SimpleDraweeView imageView) {
        EaseUser userInfo = getUserInfo(username);
        if (userInfo==null){
            imageView.setImageURI(Uri.parse("res:///"+R.drawable.ease_group_icon));
        }else {
            displayRoundIcon(context,imageView, userInfo.getAvatar(), 200,200,R.drawable.ease_group_icon);
        }
    }
    public static void setUserAvatarInChat(Context context, String username, SimpleDraweeView imageView) {
        EaseUser userInfo = getUserInfo(username);
        if (userInfo==null){
            imageView.setImageURI(Uri.parse("res:///"+R.drawable.ease_default_avatar));
        }else {
            displayRoundIcon(context, imageView, userInfo.getAvatar(), 200,200,R.drawable.ease_default_avatar);
        }
    }

    /**
     * 获取用户名
     * @param id
     * @return
     */
   public static String  getUserNickName(String id){
       EaseUser userInfo = getUserInfo(id);
       String userName="cityoff用户";
       if (userInfo!=null){
           userName=userInfo.getNickname();
       }
       return userName;
   }
    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if(textView != null){
        	EaseUser user = getUserInfo(username);
        	if(user != null && user.getNick() != null){
        		textView.setText(user.getNick());
        	}else{
        		textView.setText(username);
        	}
        }
    }
    public static void displayRoundIcon(Context context, SimpleDraweeView simpleDraweeView, String url, int width, int height,int res){
        if (simpleDraweeView==null || url==null)return;
        Uri uri=Uri.parse(url);
        RoundingParams roundingParams=new RoundingParams();
        roundingParams.setCornersRadius(10f);
        //获取GenericDraweeHierarchy对象
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width,height))

                .build();
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                .setPlaceholderImage(res)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setFailureImage(res)
                .setFailureImageScaleType(ScalingUtils.ScaleType.FIT_XY)
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
     * 加载圆形头像
     *
     * @param simpleDraweeView 显示
     */
    public static void displayIcon(SimpleDraweeView simpleDraweeView, String url, Context context) {
        if (simpleDraweeView == null || url == null) return;
        Uri uri = Uri.parse(url);
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setBorder(Color.parseColor("#fafafa"), 2);
        roundingParams.setRoundAsCircle(true);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(200, 200))
                .build();
        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                .setRoundingParams(roundingParams)
                //设置淡入淡出动画持续时间(单位：毫秒ms)
                .setFadeDuration(1000)
                .setPlaceholderImage(R.drawable.boy)
                .setFailureImage(R.drawable.boy)
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
}
