package com.yunspeak.travel.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/21 0021.
 */
public class ActivityUtils {
    private List<Activity> mList ;
    private static ActivityUtils instance;

    private ActivityUtils() {
    }

    public synchronized static ActivityUtils getInstance() {
        if (null == instance) {
            instance = new ActivityUtils();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        if (mList==null) mList=new LinkedList<>();
        mList.add(activity);
    }

    public void exit() {
        if (mList==null || mList.size()==0)return;
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mList.clear();
    }

}
