package com.example.administrator.travel.ui.appoint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.appoint.popwindow.AppointDetailMorePop;

import org.xutils.common.util.DensityUtil;

/**
 * Created by Administrator on 2016/9/14 0014.
 * 报名dialog
 */
public class EnterAppointDialog {


    public static void showDialogSuccess(Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_success, null);
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
     * @param context
     */
    public static void showDialogAddDestination(Context context) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(285),DensityUtil.dip2px(159));
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
     * 投诉
     * @param context
     */
    public static void showDialogAddComplaint(Context context, final View view) {
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
        view.setAlpha(AppointDetailMorePop.ALPHA);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(334),DensityUtil.dip2px(211));
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                view.setAlpha(0);
            }
        });
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
