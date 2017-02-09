package com.yunspeak.travel.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.yunspeak.travel.R;

/**
 * Created by wangyang on 2016/4/25.
 */
public class ToastUtils {
    //工具类不让实例化

    private ToastUtils()  {

    }
    public static Toast mToast=null;
    public static void showToast( String text){
        if (mToast==null) {
            mToast=Toast.makeText(UIUtils.getContext(),text, Toast.LENGTH_SHORT);
        }
        showLocation(text, Gravity.BOTTOM,UIUtils.getDimen(R.dimen.x60));
    }

    private static void showLocation(String text, int gra,int bottom) {
        mToast.setText(text);
        mToast.setGravity(gra, 0, bottom);
        mToast.show();
    }

    public static void showCenterToast(String text){
        if (mToast==null) {
            mToast=Toast.makeText(UIUtils.getContext(),text, Toast.LENGTH_SHORT);
        }
        showLocation(text, Gravity.CENTER,0);
    }
}
