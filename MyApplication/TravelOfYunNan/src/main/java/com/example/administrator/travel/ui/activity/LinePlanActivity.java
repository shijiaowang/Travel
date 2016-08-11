package com.example.administrator.travel.ui.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.adapter.LinePlanAdapter;
import com.example.administrator.travel.utils.FontsIconUtil;

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
    private TextView tvAddStart;
    private TextView tvAddEnd;

    @Override
    protected void initContentView() {
        tvAddStart = FontsIconUtil.findIconFontsById(R.id.tv_add_add, this);
        tvAddEnd = FontsIconUtil.findIconFontsById(R.id.tv_end_add, this);
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
        List<Line> lines=new ArrayList<>();
        for (int i=0;i<5;i++) {
            Line line = new Line();
            line.setDayNumber(i+"");
            line.setDayTime("7月"+(i+1)+"日");
            List<String> list=new ArrayList<>();
            list.add("云南.丽江");
            list.add("湖南.丽江");
            list.add("河南.丽江");
            list.add("上海.丽江");
            line.setAdd(list);
            lines.add(line);
        }
        mLvLine.setAdapter(new LinePlanAdapter(this,lines));
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
