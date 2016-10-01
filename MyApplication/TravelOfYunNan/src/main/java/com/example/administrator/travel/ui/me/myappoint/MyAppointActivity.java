package com.example.administrator.travel.ui.me.myappoint;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import org.xutils.view.annotation.ViewInject;
import java.util.List;

/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends LoadAndRefreshBaseActivity<MyAppointEvent,MyAppointBean,MyAppointBean.DataBean> {
    @ViewInject(R.id.lv_my_appoint)
    private XListView mLvMyAppoint;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_appoint;
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "我的约伴";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

    @Override
    public XListView setXListView() {
        return mLvMyAppoint;
    }

    @Override
    protected String initUrl() {
        return IVariable.MY_APPOINT;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<MyAppointBean.DataBean> httpData) {
        return new MyAppointAdapter(this,httpData.get(0).getTravel_plan());
    }
}
