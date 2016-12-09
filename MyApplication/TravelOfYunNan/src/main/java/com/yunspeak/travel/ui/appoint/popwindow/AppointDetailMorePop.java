package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/9/17.
 * 约伴详情页点击更多
 */
public class AppointDetailMorePop {
    public static void showMorePop(final Context context, final String id, View view, final String type, String collection, final ParentPopClick parentPopClick, final String shareTitle, final String shareContent,String url,View cropView) {
       showMorePopIsNotCompliant(context,id,view,type,collection,parentPopClick,shareTitle,shareContent,true,url,cropView);
    }
    public static void showMorePopIsNotCompliant(final Context context, final String id, View view, final String type, String collection, final ParentPopClick parentPopClick, final String shareTitle, final String shareContent, boolean isHasCompliant, final String url, final View cropView) {


        // 获取弹出视图对象
        final View viewPopup = View.inflate(context, R.layout.pop_appoint_detail_more, null);
        ((TextView) viewPopup.findViewById(R.id.tv_collection)).setText(collection);
        if (isHasCompliant) {
            viewPopup.findViewById(R.id.ll_complaint).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterAppointDialog.showDialogAddComplaint(context, id, type, "1", "0");
                }
            });

        }else {
            viewPopup.findViewById(R.id.ll_complaint).setVisibility(View.GONE);
        }
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.dip2px(110), LinearLayout.LayoutParams.WRAP_CONTENT);
        // 响应 视图外的地方 点击关闭当前
        viewPopup.findViewById(R.id.ll_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showShareDialog(context,shareTitle,shareContent,url,cropView);
                window.dismiss();
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

    public static void showEdit(final Context context, final ParentPopClick parentPopClick,View view) {
        // 获取弹出视图对象
        final View viewPopup = View.inflate(context, R.layout.activity_delete_edit, null);
        float width = context.getResources().getDimension(R.dimen.x110);
        float height = context.getResources().getDimension(R.dimen.x80);
        final PopupWindow window = new PopupWindow(viewPopup, (int) width, (int) height);
        // 响应 视图外的地方 点击关闭当前
        viewPopup.findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (parentPopClick!=null){
                   parentPopClick.onClick(1);
                   window.dismiss();
               }
            }
        });
        viewPopup.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(2);
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
