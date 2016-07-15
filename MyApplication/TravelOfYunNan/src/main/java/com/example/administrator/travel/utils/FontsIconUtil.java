package com.example.administrator.travel.utils;

import android.app.Activity;
import android.content.Context;
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
}
