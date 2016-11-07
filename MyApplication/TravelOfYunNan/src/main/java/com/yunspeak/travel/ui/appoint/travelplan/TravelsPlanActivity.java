package com.yunspeak.travel.ui.appoint.travelplan;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LineBean;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.LinePlanActivity;
import com.yunspeak.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.yunspeak.travel.ui.appoint.popwindow.SpinnerBean;
import com.yunspeak.travel.utils.ActivityUtils;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.JsonUtils;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangyang on 2016/8/30 0030.
 * 旅游计划
 */
public class TravelsPlanActivity extends TravelsPlanBaseActivity {

    private String trafficType = "1";//交通工具
    private List<SpinnerBean> traffics;
    private RelativeLayout mRlTraffic;
    private TextView mTvTraffic;
    private EditText mEtRemark;
    private TextView mBtSelectLine;
    private boolean isSelectLine;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
    }

    private void initTrafficeData() {
        traffics = new ArrayList<>();
        traffics.add(new SpinnerBean("汽车", "1", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("火车", "2", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("其他", "3", TRAFFIC_TYPE));
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

        mBtSelectLine = (TextView) findViewById(R.id.bt_select_line);
        mEtRemark = (EditText) findViewById(R.id.et_remark);
        mTvTraffic = (TextView) findViewById(R.id.tv_traffic);
        mRlTraffic = (RelativeLayout) findViewById(R.id.rl_traffic);
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
                AppointSpinnerPop.showSpinnerPop(mRlTraffic, traffics);
                break;
        }
    }




    @Override
    protected EditText getEdView() {
        return mEtRemark;
    }

    @Override
    protected void addChildJson(JSONObject basecJsonObject) throws Exception {
        JsonUtils.putString(IVariable.TRAFFIC,trafficType,basecJsonObject);
        JsonUtils.putString(IVariable.TRAFFIC_TEXT,getString(mEtRemark),basecJsonObject);
        if (!isSelectLine){
            throw new Exception();
        }
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
            startActivityForResult(intent,REQ_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE && resultCode==RESULT_CODE && data!=null){
            isSelectLine = data.getBooleanExtra(IVariable.DATA,false);
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