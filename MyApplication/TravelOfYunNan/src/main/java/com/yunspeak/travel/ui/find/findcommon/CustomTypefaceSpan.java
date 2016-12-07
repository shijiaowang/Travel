package com.yunspeak.travel.ui.find.findcommon;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import com.yunspeak.travel.utils.TypefaceUtis;

public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface newType;
    private final Context context;
    private  int color;
    private  int resSize;

    public CustomTypefaceSpan(String family, Context context, int color,int resSize) {
        super(family);
        newType = TypefaceUtis.getTypeface(context);
        this.context = context;
        this.color = color;
        this.resSize = resSize;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if (color!=-1) {
            ds.setColor(color);
        }
        ds.setUnderlineText(false);//是否有下划线
        applyCustomTypeFace(ds, newType);

    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    private  void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }
        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

      if (resSize!=-1) {
          int dimensionPixelSize = context.getResources().getDimensionPixelSize(resSize);
          paint.setTextSize(dimensionPixelSize);
      }

        paint.setTypeface(tf);

    }
}
