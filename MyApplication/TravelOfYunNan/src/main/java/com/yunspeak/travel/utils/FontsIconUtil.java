package com.yunspeak.travel.utils;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.ui.baseui.BaseActivity;


/**
 * Created by Administrator on 2016/7/15 0015.
 * 初始化字体图标
 */
public class FontsIconUtil {
    public static TextView findIconFontsById(int resId,Activity activity){
        TextView iconView = (TextView) activity.findViewById(resId);
        iconView.setTypeface(TypefaceUtis.getTypeface(activity));
        return iconView;
    }
    public static TextView findIconFontsById(int resId,Context context,View root){
        TextView iconView = (TextView) root .findViewById(resId);
        iconView.setTypeface(TypefaceUtis.getTypeface(context));
        return iconView;
    }

    /**
     * 可变数组设置字体图标
     * @param activity
     * @param resId
     */
    public static void findIconFontsById(Activity activity,int ... resId){
        for (Integer integer:resId) {
            TextView iconView = (TextView) activity.findViewById(integer);
            iconView.setTypeface(TypefaceUtis.getTypeface(activity));
        }
    }

    /**
     * 文字变粗
     * @param resId
     * @param context
     * @param root
     * @return
     */
    public static TextView findIconFontsByIdAndFakeBoldText(int resId,Context context,View root){
        TextView iconFontsById = findIconFontsById(resId, context, root);
        iconFontsById.getPaint().setFakeBoldText(true);
        return iconFontsById;
    }
    public static TextView findIconFontsByIdAndFakeBoldText(int resId,BaseActivity activity){
        TextView iconFontsById = findIconFontsById(resId, activity);
        iconFontsById.getPaint().setFakeBoldText(true);
        return iconFontsById;
    }

}
