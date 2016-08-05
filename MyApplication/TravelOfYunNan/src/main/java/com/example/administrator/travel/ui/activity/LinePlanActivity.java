package com.example.administrator.travel.ui.activity;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.LinePlanAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/4 0004.
 * 路线计划
 */
public class LinePlanActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_line)
    private ListView mLvLine;
    @Override
    protected void initContentView() {


    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_line_plan;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mLvLine.setAdapter(new LinePlanAdapter(this,null));
    }

    @Override
    protected String setTitleName() {
        return "路线计划";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

}
