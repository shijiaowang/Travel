package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/7/11 0011.
 * 解决滑动冲突，和惯性滑动回调
 */
public class SlippingScrollView extends ScrollView {

    private int mTouchSlop;
    private int startY;

    public SlippingScrollView(Context context) {
        this(context, null);
    }

    public SlippingScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlippingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.slipping(l,t,oldl,oldt);
        }
    }

    private SlippingListener listener;

    public void setSlippingListener(SlippingListener listener) {
        this.listener = listener;
    }

    /**
     * 包括惯性滑动在内的滚动监听器
     */
    public interface SlippingListener {
        void slipping(int l, int i, int oldl, int t);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY= ((int) ev.getRawY());
                if (Math.abs(moveY-startY)>mTouchSlop){//如果上下滑动且大于最小滑动距离，则认为是滑动，拦截事件，交给ScrollView处理
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
