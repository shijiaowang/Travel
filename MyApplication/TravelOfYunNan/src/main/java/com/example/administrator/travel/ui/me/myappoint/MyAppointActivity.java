package com.example.administrator.travel.ui.me.myappoint;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.MapUtils;

import org.xutils.view.annotation.ViewInject;
import java.util.List;

/**
 * Created by wangyang on 2016/8/1 0001.
 * 我的约伴
 */
public class MyAppointActivity extends LoadAndRefreshBaseActivity<MyAppointEvent,MyAppointTogetherBean,Object> implements View.OnClickListener {
    private static final String ENTERING="1";
    private static final String PASSED="2";
    private static final String HISTORY="3";
    private static final String WITH_ME="4";
    private String type=ENTERING;
    private String preType=ENTERING;
    @ViewInject(R.id.lv_my_appoint)
    private XListView mLvMyAppoint;
    @ViewInject(R.id.tv_entering)
    private RadioButton mTvEntering;
    @ViewInject(R.id.tv_passed)
    private RadioButton mTvPassed;
    @ViewInject(R.id.tv_with_me)
    private RadioButton mTvWithMe;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_my_appoint;
    }


    @Override
    protected Activity initViewData() {
        TextView mTvHistory = getmTvRightIcon();
        mTvHistory.setText("历史订单");
        mTvEntering.setOnClickListener(this);
        mTvEntering.setChecked(true);
        mTvPassed.setOnClickListener(this);
        mTvHistory.setOnClickListener(this);
        mTvWithMe.setOnClickListener(this);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "我的约伴";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder) {
        builder.addType(type);
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
    protected TravelBaseAdapter initAdapter(List<Object> httpData) {
        return new MyAppointAdapter(this,httpData);
    }

    @Override
    protected boolean isUserChild() {
        return true;
    }

    @Override
    protected Class<? extends ParentBean> useChildedBean() {
        if (type.equals(WITH_ME)){
            return MyAppointWithMeBean.class;
        }
        return MyAppointTogetherBean.class;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_passed:
                type=PASSED;
                toRefresh();
                break;
            case R.id.tv_entering:
                type=ENTERING;
                toRefresh();
                break;
            case R.id.tv_with_me:
                type=WITH_ME;
                toRefresh();
                break;
            case R.id.tv_right_icon:
                type=HISTORY;
                toRefresh();
                break;
        }
    }

    private void toRefresh() {
        if (type.equals(preType))return;
        preType=type;
        onLoad(TYPE_REFRESH);
    }
}
