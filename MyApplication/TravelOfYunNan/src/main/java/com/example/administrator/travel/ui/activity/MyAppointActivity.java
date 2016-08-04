package com.example.administrator.travel.ui.activity;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.MyAppoint;
import com.example.administrator.travel.ui.adapter.MyAppointAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class MyAppointActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_my_appoint)
    private ListView mLvMyAppoint;

    @Override
    protected void initContentView() {

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_appoint;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        List<MyAppoint> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyAppoint appoint=new MyAppoint();
            if (i % 2 == 0) {
                appoint.setIsSuccess(true);
            }
            list.add(appoint);
        }
         mLvMyAppoint.setAdapter(new MyAppointAdapter(this,list));
    }

    @Override
    protected String setTitleName() {
        return "我的约伴";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
