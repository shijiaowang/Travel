package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.administrator.travel.utils.LogUtils;

import java.util.Scanner;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class SlippingScrollView extends ScrollView {
    public SlippingScrollView(Context context) {
        super(context);
    }

    public SlippingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlippingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.slipping();
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
        void slipping();
    }
}
