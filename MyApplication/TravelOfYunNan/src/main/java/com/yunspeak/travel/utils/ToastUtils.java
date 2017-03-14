package com.yunspeak.travel.utils;

import android.view.Gravity;
import android.widget.Toast;

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
        mToast.setText(text);
        mToast.show();
    }
    public static void showToast(int res){
        showToast(UIUtils.getString(res));
    }

}
