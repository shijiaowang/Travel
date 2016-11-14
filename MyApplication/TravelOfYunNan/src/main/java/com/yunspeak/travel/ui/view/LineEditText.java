package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2016/8/17 0017.
 */
public class LineEditText extends EditText {

    private Paint mPaint;
    private int lineHeight;

    public LineEditText(Context context) {
        this(context, null);
    }

    public LineEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        lineHeight = UIUtils.dip2px(1f, getContext());
        mPaint.setColor(getContext().getResources().getColor(R.color.colorFAFAFA));
        mPaint.setStrokeWidth(UIUtils.dip2px(1f, getContext()));
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (focusChangeListener != null) {
                    focusChangeListener.onFocusChange(v, hasFocus);
                }
                if (hasFocus) {
                    mPaint.setStrokeWidth(UIUtils.dip2px(2f,getContext()));
                } else {
                    mPaint.setStrokeWidth(UIUtils.dip2px(1f, getContext()));
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//      画底线

        canvas.drawLine(0, this.getHeight() - lineHeight, this.getWidth(), this.getHeight(), mPaint);
    }
    private FocusChangeListener focusChangeListener;
    public interface FocusChangeListener{
        void onFocusChange(View v, boolean hasFocus);
    }

    public void setFocusChangeListener(FocusChangeListener focusChangeListener) {
        this.focusChangeListener = focusChangeListener;
    }
}
