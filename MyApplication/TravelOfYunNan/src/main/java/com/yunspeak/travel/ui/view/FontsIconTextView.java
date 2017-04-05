package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yunspeak.travel.utils.TypefaceUtis;

/**
 * Created by wangyang on 2016/8/19 0019.
 * 字体图标textview
 */
public class FontsIconTextView extends TextView {
    public FontsIconTextView(Context context) {
        this(context, null);
    }

    public FontsIconTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontsIconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(TypefaceUtis.getTypeface(getContext()));
    }

}
