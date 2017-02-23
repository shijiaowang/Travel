package com.yunspeak.travel.ui.view.dateview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangyang on 2017/2/23.
 */

public class DateRecycleView extends RecyclerView {
    public DateRecycleView(Context context) {
        this(context,null);
    }

    public DateRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DateRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void init(int limit){
        Calendar start=Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        Date date=new Date();
        start.setTime(date);
        end.setTime(date);
        end.add(Calendar.MONTH,5);
    }
}
