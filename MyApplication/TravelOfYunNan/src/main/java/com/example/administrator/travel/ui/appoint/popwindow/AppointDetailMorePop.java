package com.example.administrator.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;


import org.xutils.common.util.DensityUtil;

/**
 * Created by android on 2016/9/17.
 * 约伴详情页点击更多
 */
public class AppointDetailMorePop {
    public static final float ALPHA=0.7f;
    public static void showMorePop(final Context context, View view) {
        // 获取弹出视图对象
        final View viewPopup = View.inflate(context, R.layout.pop_appoint_detail_more, null);
        final View vRoot = viewPopup.findViewById(R.id.root);
        viewPopup.findViewById(R.id.ll_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSharePop(context, vRoot);
            }
        });
        viewPopup.findViewById(R.id.ll_complaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showDialogAddComplaint(context,vRoot);
            }
        });

        // 创建 弹出窗口
        PopupWindow window = new PopupWindow(viewPopup, DensityUtil.dip2px(110), DensityUtil.dip2px(114));
        // 响应 视图外的地方 点击关闭当前

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
        window.showAsDropDown(view);
    }

    /**
     * 分享
     *
     * @param context
     * @param viewRoot
     */
    public static void showSharePop(final Context context,  final View viewRoot) {
        // 获取弹出视图对象
        final View viewPopup = View.inflate(context, R.layout.pop_share, null);
        View rlBottom = ((Activity) context).findViewById(R.id.rl_bottom);

        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(200));
        // 响应 视图外的地方 点击关闭当前
        // 设置背景颜色变暗
        viewRoot.setAlpha(ALPHA);
        viewPopup.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                viewRoot.setAlpha(0f);
            }
        });
        window.setOutsideTouchable(true);
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)
        ));
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.popDownToUp);
        int[] location = new int[2];
        rlBottom.getLocationInWindow(location);

        window.showAtLocation(rlBottom, Gravity.TOP, 0, location[1] - DensityUtil.dip2px(200) + rlBottom.getHeight());

    }
}
