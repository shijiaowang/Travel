package com.yunspeak.travel.ui.appoint.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/9/14 0014.
 * 排序
 */
public class AppointOrderPop {

    private PopupWindow window;

    public  void showOrderPop(final Context context, View view, String[] title, int clickPosition){
           // 获取弹出视图对象
           View viewPopup = View.inflate(context, R.layout.pop_window_order, null);


           // 创建 弹出窗口
           window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(49));
           final RadioGroup radioGroup = (RadioGroup) viewPopup;
        if (clickPosition>=0 && clickPosition<radioGroup.getChildCount()){
            ((RadioButton) radioGroup.getChildAt(clickPosition)).setChecked(true);
        }
        if (title!=null){
            int j=0;
            for (int i=0;i<radioGroup.getChildCount();i++){
                if (!(radioGroup.getChildAt(i) instanceof RadioButton))continue;
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setText(title[j]);
                j++;
                j=j>title.length-1?title.length-1:j;
            }
        }
           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, int checkedId) {
                   int indexOfChild = radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));
                   if (onItemClickListener != null) {
                       onItemClickListener.onItemClick(indexOfChild);
                   }
                   window.dismiss();
               }
           });
           // 响应 视图外的地方 点击关闭当前
           window.setOutsideTouchable(true);

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
           // 响应返回键的关闭
           window.setFocusable(true);
           window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
       window.showAsDropDown(view);
    }
    public interface OnItemClickListener{
        void onItemClick(int type);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
