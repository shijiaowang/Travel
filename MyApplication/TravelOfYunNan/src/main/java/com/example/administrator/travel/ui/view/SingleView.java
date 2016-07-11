package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class SingleView extends LinearLayout {
    private TextView mTvPlace;
    private ImageView mIvPic;
    private boolean isChecked;

    public SingleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public SingleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SingleView(Context context) {
        super(context);
        initView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setChecked(false);
    }

    private void initView(Context context){
        // 填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_fragment_circle_nav_left, this, true);
        mTvPlace = (TextView) v.findViewById(R.id.tv_place);
        mIvPic = (ImageView) v.findViewById(R.id.iv_cursor);
    }


    public void setChecked( boolean checked) {
        isChecked=checked;
        mTvPlace.setTextColor(checked? Color.YELLOW:Color.RED);
        invalidate();
    }


    public boolean isChecked() {
        return isChecked;
    }


    /**
     * 设置文字
     * @param text
     */
    public void setTitle(String text){
        mTvPlace.setText(text);
    }
}