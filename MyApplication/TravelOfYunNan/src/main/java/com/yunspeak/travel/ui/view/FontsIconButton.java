package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.yunspeak.travel.utils.TypefaceUtis;

/**
 * Created by Administrator on 2016/9/19 0019.
 * 字体图标按钮
 */
public class FontsIconButton extends Button {
    public FontsIconButton(Context context) {
        this(context,null);
    }

    public FontsIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(TypefaceUtis.getTypeface(getContext()));
    }

    public FontsIconButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
}
