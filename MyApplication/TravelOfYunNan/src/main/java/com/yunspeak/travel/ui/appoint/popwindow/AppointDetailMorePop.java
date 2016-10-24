package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.PricebasecBean;


import org.xutils.common.util.DensityUtil;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by android on 2016/9/17.
 * 约伴详情页点击更多
 */
public class AppointDetailMorePop {
    public static final float ALPHA=0.7f;
    public static void showMorePop(final Context context, View view, String collection, final ParentPopClick parentPopClick) {
        // 获取弹出视图对象
        final View viewPopup = View.inflate(context, R.layout.pop_appoint_detail_more, null);

        viewPopup.findViewById(R.id.ll_complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showDialogAddComplaint(context);
            }
        });
        ((TextView) viewPopup.findViewById(R.id.tv_collection)).setText(collection);

        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.dip2px(110), DensityUtil.dip2px(114));
        // 响应 视图外的地方 点击关闭当前
        viewPopup.findViewById(R.id.ll_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // EnterAppointDialog.showShareDialog(context);
                ShareSDK.initSDK(context);
                OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(context.getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
                oks.show(context);

            }
        });
        viewPopup.findViewById(R.id.ll_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(0);
                window.dismiss();
            }
        });
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha=0.7f;
        ((Activity) context).getWindow().setAttributes(lp);

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);


            }
        });
        window.setOutsideTouchable(true);

        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
        window.showAsDropDown(view,DensityUtil.getScreenWidth()-DensityUtil.dip2px(125),0);
    }


}
