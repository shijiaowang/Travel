package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;


import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class DottedLineView extends View {

    private Paint mPaint;
    private Path mPath;

    public DottedLineView(Context context) {
        this(context, null);
    }

    public DottedLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DottedLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.otherTitleBg));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFakeBoldText(true);
        mPath = new Path();
        /*数组单数代表线条长度，双数位代表空余长度，最后1代表偏移量*/
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mPaint.setPathEffect(effects);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(getWidth() / 2, 0);
        mPath.lineTo(getWidth() / 2, getHeight());
        canvas.drawPath(mPath, mPaint);

    }
}
