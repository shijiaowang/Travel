package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class WrapViewPager extends ViewPager {
    public int[] index = new int[3];
    private int currentPosition = 0;
    private int prePosition=0;



    public WrapViewPager(Context context) {
        this(context, null);
    }

    public WrapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
           if (currentPosition<1){
               LogUtils.e("---------------------------------------------------------------------------------");
               LogUtils.e("第"+i+"個由"+index[i]+"改為"+h+"當前為"+i);
               index[i]=h;

           }else if (currentPosition>=1 ){
               if (currentPosition>prePosition && i==1) {
                   LogUtils.e("大第"+currentPosition+"個由"+index[currentPosition]+"改為"+h+"當前為"+i);
                   index[currentPosition] = h;
               }else if (currentPosition<prePosition){
                   LogUtils.e("小第"+currentPosition+"個由"+index[currentPosition]+"改為"+h+"當前為"+i);
                   index[currentPosition] = h;
               }
           }

        }
        for (int i = 0; i < index.length; i++) {
            LogUtils.e("當前存儲頁面" + i + "的高度為" + index[i]);
        }
        LogUtils.e("当前页面" + currentPosition + "前一個頁面" + prePosition + "孩子個數" + getChildCount());
        LogUtils.e("---------------------------------------------------------------------------------");
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(index[currentPosition], MeasureSpec.EXACTLY);
        prePosition=currentPosition;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isFull() {
        for (int i = 0; i < index.length; i++) {
            if (index[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
