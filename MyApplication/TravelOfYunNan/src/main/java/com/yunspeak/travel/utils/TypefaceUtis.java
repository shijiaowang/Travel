package com.yunspeak.travel.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class TypefaceUtis {
    public static Typeface getTypeface(Context mContext){
        return  Typeface.createFromAsset(mContext.getAssets(), "fonts/icomoon.ttf");
    }
}
