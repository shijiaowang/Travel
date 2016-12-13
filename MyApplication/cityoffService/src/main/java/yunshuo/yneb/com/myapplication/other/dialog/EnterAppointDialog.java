package yunshuo.yneb.com.myapplication.other.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunshuo.yneb.com.myapplication.ParentPopClick;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.other.utils.UIUtils;

/**
 * Created by wangyang on 2016/9/14 0014.
 * 报名dialog
 */
public class EnterAppointDialog {




    public static void showCommonDialog(Context context, String title, String okText, String content, final ParentPopClick parentPopClick) {
        //创建视图
        View dialogView = View.inflate(context, R.layout.dialog_appoint_enlist_fail, null);
        final Dialog dialog = new Dialog(context, R.style.noTitleDialog);
        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogView.findViewById(R.id.tv_content)).setText(UIUtils.getString(R.string.kongge) + content);
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
        showEndCommon(dialogView, dialog,R.dimen.x285,R.dimen.x179);
    }
    private static void showEndCommon(View dialogView, Dialog dialog, int resWidth,int resHeiht) {
        Window window = dialog.getWindow(); //得到对话框
        if (window!=null) {
            window.setGravity(Gravity.CENTER);
        }
        Context context = dialog.getContext();
        float dimension = context.getResources().getDimension(resWidth);
        float height = context.getResources().getDimension(resHeiht);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) dimension, (int) height);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
