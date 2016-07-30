package com.example.administrator.travel.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.ui.activity.BaseActivity;
import com.example.administrator.travel.ui.activity.MessageCenterActivity;

/**
 * Created by Administrator on 2016/7/15 0015.
 * 初始化字体图标
 */
public class FontsIconUtil {
    public static TextView findIconFontsById(int resId,BaseActivity activity){
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
