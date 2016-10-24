package com.yunspeak.travel.ui.appoint.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;

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
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

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


}
