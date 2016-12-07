package com.yunspeak.travel.ui.find.active.activedetail;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by wangyang on 2016/12/7 0007.
 */

public class ChangeColorSpan extends ClickableSpan {
     int color;

    public ChangeColorSpan(int color) {
        this.color = color;
    }

    @Override
    public void onClick(View widget) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(color);
        ds.setUnderlineText(false);//是否有下划线
    }
}