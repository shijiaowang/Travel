package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 避免快速点击的button
 */
public class AvoidFastButton extends Button{

    private long preClickTime=0;

    public AvoidFastButton(Context context) {
        this(context,null);
    }

    public AvoidFastButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AvoidFastButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initListener();
    }

    private void initListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis()-preClickTime>700 && listener!=null){
                    listener.onClick(v);
                }
                preClickTime = System.currentTimeMillis();
            }
        });
    }
    public interface AvoidFastOnClickListener{
        void onClick(View v);
    }
    private AvoidFastOnClickListener listener;
    public void setOnAvoidFastOnClickListener(AvoidFastOnClickListener listener){
        this.listener=listener;
    }

}
