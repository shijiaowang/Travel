package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yunspeak.travel.R;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by wangyang on 2016/9/20 0020.
 */
public class AppointSpinnerPop {
    public static void showSpinnerPop(final Context context, View view, final List<SpinnerBean> text) {
        if (text==null || text.size()==0)return;
        // 获取弹出视图对象
         ListView viewPopup = ((ListView) View.inflate(context, R.layout.pop_spinner, null));
         viewPopup.setAdapter(new SpinnerPopAdapter(text,context));

        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.dip2px(105), ViewGroup.LayoutParams.WRAP_CONTENT);
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
        viewPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(text.get(position));
                  window.dismiss();
            }
        });
        window.setOutsideTouchable(true);
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
        // 获取控件的坐标 x y
        window.showAsDropDown(view);
    }


}
