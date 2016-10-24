package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.yunspeak.travel.R;


/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class DottedLineView extends View {

    private Paint mPaint;
    private Path mPath;
    private int currentHeight=0;
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
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 2);
        mPaint.setPathEffect(effects);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mPath.moveTo(0, 0);
        mPath.lineTo(0,getHeight());
        canvas.drawPath(mPath, mPaint);
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    private int getRightValue() {
        return this.currentHeight ==0?getMeasuredHeight(): this.currentHeight;
    }


}

