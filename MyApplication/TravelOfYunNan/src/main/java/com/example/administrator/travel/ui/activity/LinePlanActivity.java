package com.example.administrator.travel.ui.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.LinePlanAdapter;


import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 * 路线计划
 */
public class LinePlanActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_line)
    private ListView mLvLine;
    @ViewInject(R.id.tv_start_add)
    private TextView tvAddStart;
    @ViewInject(R.id.tv_end_add)
    private TextView tvAddEnd;
    private ArrayList<String> dayList;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_line_plan;
    }

    @Override
    protected void initEvent() {
        dayList = getIntent().getStringArrayListExtra(IVariable.DATA);
    }

    @Override
    protected void initViewData() {
        mLvLine.setAdapter(new LinePlanAdapter(this,dayList));
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
