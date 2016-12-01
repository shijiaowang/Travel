package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.DynamicLayout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yunspeak.travel.R;

import java.lang.reflect.Field;

/**
 * Created by wangyang on 2016/12/1 0001.
 */
public class EllipsizeTextView extends TextView {

    private int limitLines=1;

    public EllipsizeTextView(Context context) {
        this(context,null);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EllipsizeTextView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.EllipsizeTextView_m_limit_lines:
                    limitLines = typedArray.getInt(index,1);
                    break;
            }

        }
        typedArray.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        StaticLayout layout = null;
        Field field = null;
        try {
            Field staticField = DynamicLayout.class.getDeclaredField("sStaticLayout");
            staticField.setAccessible(true);
            layout = (StaticLayout) staticField.get(DynamicLayout.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (layout != null) {
            try {
                field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
                field.setAccessible(true);
                field.setInt(layout, limitLines);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (layout != null && field != null) {
            try {
                field.setInt(layout, Integer.MAX_VALUE);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}