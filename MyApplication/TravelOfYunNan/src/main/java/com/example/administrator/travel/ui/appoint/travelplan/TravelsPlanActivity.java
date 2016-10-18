package com.example.administrator.travel.ui.appoint.travelplan;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;

import com.example.administrator.travel.ui.appoint.lineplan.LineBean;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanActivity;
import com.example.administrator.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.example.administrator.travel.ui.appoint.popwindow.SpinnerBean;

import com.example.administrator.travel.utils.ActivityUtils;

import com.example.administrator.travel.utils.CalendarUtils;

import com.example.administrator.travel.utils.JsonUtils;


import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangyang on 2016/8/30 0030.
 * 旅游计划
 */
public class TravelsPlanActivity extends TravelsPlanBaseActivity {
    @ViewInject(R.id.bt_select_line)
    private TextView mBtSelectLine;
    @ViewInject(R.id.et_remark)
    private EditText mEtRemark;
    @ViewInject(R.id.tv_traffic)
    private TextView mTvTraffic;
    @ViewInject(R.id.rl_traffic)
    private RelativeLayout mRlTraffic;
    private String trafficType = "1";//交通工具
    private List<SpinnerBean> traffics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
    }

    private void initTrafficeData() {
        traffics = new ArrayList<>();
        traffics.add(new SpinnerBean("自驾游", "1", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("火车", "2", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("汽车", "3", TRAFFIC_TYPE));
        mRlTraffic.setOnClickListener(this);
    }



    @Override
    protected boolean isHideRight() {
        return false;
    }



    @Override
    protected int initChildLayoutRes() {
        return R.layout.activity_travels_plan;
    }

    @Override
    protected void initChildEvent() {
        initTrafficeData();
        mBtSelectLine.setOnClickListener(this);
    }

    @Override
    protected void childClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_line:
                if (checkTimeAgain()) {
                    calculationDay();
                }
                break;
            case R.id.rl_traffic:
                AppointSpinnerPop.showSpinnerPop(this, mRlTraffic, traffics);
                break;
        }
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected void onSuccess(HttpEvent httpEvent) {

    }



    @Override
    protected EditText getEdView() {
        return mEtRemark;
    }

    @Override
    protected void addChildJson(JSONObject basecJsonObject) throws Exception {
        JsonUtils.putString(IVariable.TRAFFIC,trafficType,basecJsonObject);
        JsonUtils.putString(IVariable.TRAFFIC_TEXT,getString(mEtRemark),basecJsonObject);
    }


    /**
     * 计算日期，启动路线规划页面
     */
    private void calculationDay() {
        try {
            if (!(endLine != null && startLine != null && endLine == endDate && startLine == startDate)) {
                List<LineBean> lineBeans = new ArrayList<>();
                String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                int countDay = Integer.parseInt(howDay);
                lineBeans.add(new LineBean(""));
                for (int i = 0; i <= countDay + 1; i++) {//这里判断条件是因为添加了集合地和解散地
                    if (i == countDay) {
                        lineBeans.add(new LineBean(""));
                        break;
                    }
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    LineBean lineBean = new LineBean(month + "月" + day + "日");
                    lineBean.setDate((calendar.getTime().getTime()/1000) + "");
                    lineBeans.add(lineBean);
                    calendar.add(Calendar.DATE, 1);
                }
                if (GlobalValue.mLineBeans == null) {
                    GlobalValue.mLineBeans = lineBeans;
                } else {
                    for (int i = 0; i < lineBeans.size(); i++) {
                        for (int j = 0; j < GlobalValue.mLineBeans.size(); j++) {
                            if (lineBeans.get(i).getTime().equals(GlobalValue.mLineBeans.get(j).getTime())) {
                                lineBeans.add(i, GlobalValue.mLineBeans.get(j));
                                lineBeans.remove(i + 1);
                                break;
                            }
                        }
                    }
                    GlobalValue.mLineBeans = lineBeans;
                    lineBeans = null;
                }
                endLine = endDate;
                startLine = startDate;
            }
            Intent intent = new Intent(this, LinePlanActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @Subscribe
    public void onEvent(SpinnerBean spinnerBean) {
        if (spinnerBean.getSpinnerId() == TRAFFIC_TYPE) {
            trafficType = spinnerBean.getId();
            mTvTraffic.setText(spinnerBean.getType());
        }
    }
}