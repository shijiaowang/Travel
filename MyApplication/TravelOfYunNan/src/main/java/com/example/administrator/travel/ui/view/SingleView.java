package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
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


    private void initView(Context context){
        // 填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_fragment_circle_nav_left, this, true);
        mTvPlace = (TextView) v.findViewById(R.id.tv_place);
        mIvPic = (ImageView) v.findViewById(R.id.iv_cursor);
    }


    public void setChecked() {
        mTvPlace.setTextColor(Color.GREEN);
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