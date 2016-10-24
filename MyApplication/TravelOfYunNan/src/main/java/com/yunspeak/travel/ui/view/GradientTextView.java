package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.TypefaceUtis;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 渐变色的textView
 */
public class GradientTextView extends TextView {
    private boolean isChecked=false;//是否选中

    private LinearGradient linearGradient;
    private int normalColor;
    private int upColor;
    private int downColor;

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        setTypeface(TypefaceUtis.getTypeface(context));
    }

    private void init(Context context,AttributeSet attrs,int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数

        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.GradientTextView_gradient_normal_color:
                    normalColor = typedArray.getColor(index, Color.parseColor("#646464"));
                    break;
                case R.styleable.GradientTextView_gradient_checked_up_color:
                    upColor = typedArray.getColor(index, Color.parseColor("#60edb3"));
                    break;
                case R.styleable.GradientTextView_gradient_checked_down_color:
                    downColor = typedArray.getColor(index, Color.parseColor("#64d6fb"));
                    break;
            }

        }
        typedArray.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (isChecked){
            if (linearGradient==null){
                linearGradient = new LinearGradient(0, 0, 0, getHeight(),upColor, downColor, Shader.TileMode.CLAMP);
            }
            getPaint().setShader(linearGradient);
        }else {
            getPaint().setShader(null);
            getPaint().setColor(normalColor);
        }

        super.onDraw(canvas);
    }
    public void setChecked(boolean isChecked){
        this.isChecked=isChecked;
        if (Looper.getMainLooper()==Looper.myLooper()){
            invalidate();
        }else {
            postInvalidate();
        }

    }

    /**
     * 恢复
     * @param state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            this.isChecked=bundle.getBoolean(DIV_SAVE);
            super.onRestoreInstanceState(bundle.getParcelable(SYSTEM_SAVE));
            return;

        }
        super.onRestoreInstanceState(state);
    }

    private static final String SYSTEM_SAVE="system_save";//系统存储的
    private static final String DIV_SAVE="div_save";//自定义存储
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(SYSTEM_SAVE,super.onSaveInstanceState());
        bundle.putBoolean(DIV_SAVE, isChecked);
        return bundle;
    }
}
