package com.yunspeak.travel.ui.appoint.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.TypefaceUtis;
import com.yunspeak.travel.utils.UIUtils;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by wangyang on 2016/9/14 0014.
 * 报名dialog
 */
public class EnterAppointDialog {


    public static void showDialogSuccess(final Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_success, null);
       final Dialog dialog = new Dialog(context,R.style.noTitleDialog);

        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_my_appoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyAppointActivity.class));
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(179));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }

    /**
     * 添加目的地
     * @param isStart
     * @param context
     */
    public static void showDialogAddDestination(final Context context, final TextView textView, final boolean isStart) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
        mEtDestination.requestFocus();
        UIUtils.setEmojiFilter(mEtDestination);
        dialogView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEtDestination.getText().toString().trim();
                if (StringUtils.isEmpty(trim)){
                    ToastUtils.showToast("目的地不能为空!");
                    return;
                }

                try {
                    JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
                    String key=isStart?IVariable.MEET_ADDRESS:IVariable.OVER_ADDRESS;
                    JsonUtils.putString(key,trim, basecJsonObject);
                    textView.setText(trim);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 if (StringUtils.isEmpty(trim)){
                     textView.setBackgroundResource(R.drawable.activity_line_plan_add_bg);
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                     textView.setTypeface(TypefaceUtis.getTypeface(context));
                 }else {
                     textView.setBackgroundColor(Color.TRANSPARENT);
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                     textView.setTypeface(Typeface.DEFAULT);
                 }
                dialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(159));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    /**
     * 投诉
     * @param context
     */
    public static void showDialogAddComplaint(Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_complaint, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);

        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(334),DensityUtil.dip2px(211));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }
    /**
     * 分享
     *  @param context
     *
     */
    public static void showShareDialog(final Context context) {
        // 获取弹出视图对象
        final View dialogView = View.inflate(context, R.layout.pop_share, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        View rlBottom = ((Activity) context).findViewById(R.id.rl_bottom);
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_sina).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        ShareSDK.initSDK(context);
                        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                        //oks.disableSSOWhenAuthorize();
                        oks.setPlatform(SinaWeibo.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                        oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                        oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                        oks.setComment("哇，不得了啦，有美女哟");
// 启动分享GUI
                        oks.show(context);


            }
        });
        dialogView.findViewById(R.id.tv_we_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                //oks.disableSSOWhenAuthorize();
                oks.setPlatform(Wechat.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://yunspeak.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("哇，不得了啦，有美女哟");
                oks.show(context);
            }
        });
        dialogView.findViewById(R.id.tv_we_moment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                //oks.disableSSOWhenAuthorize();
                oks.setPlatform(WechatMoments.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("云说约伴分享");
// text是分享文本，所有平台都需要这个字段
                oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://yunspeak.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("哇，不得了啦，有美女哟");
                oks.show(context);
            }
        });
         dialogView.findViewById(R.id.tv_qq_zone).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ShareSDK.initSDK(context);
                 OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                 //oks.disableSSOWhenAuthorize();
                 oks.setPlatform(QZone.NAME);
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                 oks.setTitle("云说约伴分享");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
                 oks.setTitleUrl("http://yunspeak.cn");
// text是分享文本，所有平台都需要这个字段
                 oks.setText("这是一个不得了的约伴分享");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
               //  oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                 oks.setComment("哇，不得了啦，有美女哟");
// site是分享此内容的网站名称，仅在QQ空间使用
                 oks.setSite(context.getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
                 oks.setSiteUrl("http://yunspeak.cn");
// 启动分享GUI
                 oks.show(context);
             }
         });
        WindowManager.LayoutParams wl = window.getAttributes();
        int[] location = new int[2];
        rlBottom.getLocationInWindow(location);
        wl.x = 0;
        wl.y = location[1];
        //设置显示位置
        dialog.onWindowAttributesChanged(wl);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(),DensityUtil.dip2px(200));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();

    }
    public static void showCommonDialog(final Context context, String title, String okText, String content, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_fail, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.tv_content)).setText(context.getString(R.string.kongge)+content);
        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        tvSure.setText(okText);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(0);
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(179));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }


}
