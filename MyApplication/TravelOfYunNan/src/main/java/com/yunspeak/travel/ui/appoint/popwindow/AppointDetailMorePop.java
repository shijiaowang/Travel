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
               EnterAppointDialog.showShareDialog(context);


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
