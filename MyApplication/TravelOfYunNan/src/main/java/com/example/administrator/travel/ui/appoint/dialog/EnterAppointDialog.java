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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanEvent;
import com.example.administrator.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
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
     * @param isStart
     * @param context
     * @param b
     */
    public static void showDialogAddDestination(Context context, final TextView textView, final boolean isStart) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_add_destination, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
        final EditText mEtDestination = (EditText) dialogView.findViewById(R.id.et_destination);
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
                    LogUtils.e(basecJsonObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView.setText(trim);
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
