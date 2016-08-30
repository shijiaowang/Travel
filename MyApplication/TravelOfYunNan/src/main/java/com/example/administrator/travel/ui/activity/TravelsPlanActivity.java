package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 旅游计划
 */
public class TravelsPlanActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_select_line)
    private TextView mTvSelectLine;
    private TextView mTvRightNext;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan;
    }

    @Override
    protected void initEvent() {
        init();
       mTvSelectLine.setOnClickListener(this);
    }

    private void init() {
        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        mTvRightNext.setText(R.string.next);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "旅行计划";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_select_line:
                startActivity(new Intent(this,LinePlanActivity.class));
                break;
        }
    }
}
