package com.yunspeak.travel.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by wangyang on 2017/4/5.
 */

public class OpenActivityUtils {
    private static OpenActivityUtils activityUtils =new OpenActivityUtils();
    private OpenActivityUtils(){

    }
    public static OpenActivityUtils getInstance(){
        return activityUtils;
    }

    public void startAnimation(Intent intent, Context context){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP && context instanceof Activity){
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(((Activity) context)).toBundle());
        }else {
            context.startActivity(intent);
        }

    }
}
