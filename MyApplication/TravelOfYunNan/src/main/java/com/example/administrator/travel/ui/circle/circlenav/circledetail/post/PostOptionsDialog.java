package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.ParentPopClick;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/10/18 0018.
 * 帖子操作
 */

public class PostOptionsDialog {
    public static final int TYPE_REPLY=1;
    public static final int TYPE_REPORT=2;
    public static final int TYPE_COLLECTION=3;
    public static final int TYPE_ZAN=4;
    public static void showCommonDialog(final Context context, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.activity_post_options_dialog, null);
        final Dialog dialog = new Dialog(context,R.style.noTitleDialog);
         dialogView.findViewById(R.id.tv_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(TYPE_REPLY);
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_zan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(TYPE_ZAN);
                dialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentPopClick.onClick(TYPE_REPORT);
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogAnima); //设置窗口弹出动画
        window.setGravity(Gravity.CENTER);

        //创建 Dialog
//		Dialog dialog=new Dialog(上下文,风格style);

        //layout_width layout_height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(250),DensityUtil.dip2px(119));
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
