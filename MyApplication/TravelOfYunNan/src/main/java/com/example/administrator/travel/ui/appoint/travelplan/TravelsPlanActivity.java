package com.example.administrator.travel.ui.appoint.travelplan;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.TravelsApplication;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.appoint.cutpicture.CutPictureActivity;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentActivity;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;
import com.example.administrator.travel.ui.appoint.lineplan.LinePlanActivity;
import com.example.administrator.travel.ui.appoint.popwindow.AppointSpinnerPop;
import com.example.administrator.travel.ui.appoint.popwindow.SpinnerBean;
import com.example.administrator.travel.ui.view.GradientTextView;
import com.example.administrator.travel.utils.ActivityUtils;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 旅游计划
 */
public class TravelsPlanActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.bt_select_line)
    private TextView mBtSelectLine;
    @ViewInject(R.id.tv_start)
    private TextView mTvStart;
    @ViewInject(R.id.tv_end)
    private TextView mTvEnd;
    @ViewInject(R.id.tv_end_time)
    private TextView mTvEndTime;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_start_time)
    private TextView mTvStartTime;
    @ViewInject(R.id.tv_how_day)
    private TextView mTvHowDay;
    @ViewInject(R.id.et_remark)
    private EditText mEtRemark;
    @ViewInject(R.id.tv_start_morning)
    private GradientTextView mTvStartMorning;
    @ViewInject(R.id.tv_start_night)
    private GradientTextView mTvStartNight;
    @ViewInject(R.id.tv_end_morning)
    private GradientTextView mTvEndMorning;
    @ViewInject(R.id.tv_end_night)
    private GradientTextView mTvEndNight;
    @ViewInject(R.id.rl_start_morning)
    private RelativeLayout mRlStartMorning;
    @ViewInject(R.id.rl_start_night)
    private RelativeLayout mRlStartNight;
    @ViewInject(R.id.rl_end_morning)
    private RelativeLayout mRlEndMorning;
    @ViewInject(R.id.rl_end_night)
    private RelativeLayout mRlEndNight;
    @ViewInject(R.id.tv_traffic)
    private TextView mTvTraffic;
    @ViewInject(R.id.rl_traffic)
    private RelativeLayout mRlTraffic;
    @ViewInject(R.id.tv_icon)
    private TextView mTvIcon;
    private TextView mTvRightNext;
    private TimePickerView pvTime;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private String trafficType = "1";//交通工具
    private int dayOfYear;
    private int year;
    private Date endLine;
    private Date startLine;
    private List<SpinnerBean> traffics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.getInstance().addActivity(this);
        registerEventBus(this);

    }

    private void initTrafficeData() {
        traffics = new ArrayList<>();
        traffics.add(new SpinnerBean("自驾游","1", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("火车","2", TRAFFIC_TYPE));
        traffics.add(new SpinnerBean("汽车","3", TRAFFIC_TYPE));
        mRlTraffic.setOnClickListener(this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan;
    }

    @Override
    protected void initEvent() {

        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        init();
        initTrafficeData();
        mTvRightNext.setOnClickListener(this);
        mBtSelectLine.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        mRlStartMorning.setOnClickListener(this);
        mRlEndMorning.setOnClickListener(this);
        mRlStartNight.setOnClickListener(this);
        mRlEndNight.setOnClickListener(this);
        mTvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TravelsPlanActivity.this, CutPictureActivity.class));
            }
        });
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
        switch (v.getId()) {
            case R.id.bt_select_line:
                if (checkTimeAgain()) {
                    calculationDay();
                }
                break;
            case R.id.tv_start:
                showTime(mTvStartTime);
                break;
            case R.id.tv_end:
                showTime(mTvEndTime);
                break;
            case R.id.tv_right_icon:
            case R.id.bt_next:
                if (checkTimeAgain()) {
                    addJson();
                }
                break;
            case R.id.rl_start_morning:
                mTvStartMorning.setChecked(true);
                mTvStartNight.setChecked(false);
                break;
            case R.id.rl_start_night:
                mTvStartMorning.setChecked(false);
                mTvStartNight.setChecked(true);
                break;
            case R.id.rl_end_morning:
                mTvEndMorning.setChecked(true);
                mTvEndNight.setChecked(false);
                break;
            case R.id.rl_end_night:
                mTvEndMorning.setChecked(false);
                mTvEndNight.setChecked(true);
                break;
            case R.id.rl_traffic:
                AppointSpinnerPop.showSpinnerPop(this,mRlTraffic,traffics);
               break;
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
                    lineBean.setDate(calendar.getTime().getTime() + "");
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

    /**
     * 添加json
     */
    private void addJson() {
        try {
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            JsonUtils.putString(IVariable.USER_ID, GlobalUtils.getUserInfo().getId(), basecJsonObject);
            JsonUtils.putString(IVariable.START_TIME, startDate.getTime()/1000 + "", basecJsonObject);
            JsonUtils.putString(IVariable.END_TIME, endDate.getTime()/1000 + "", basecJsonObject);
            JsonUtils.putString(IVariable.TRAFFIC, trafficType, basecJsonObject);
            basecJsonObject.put(IVariable.TRAFFIC_TEXT, getTrafficText());
            LogUtils.e(basecJsonObject.toString());
            startActivity(new Intent(this, PersonnelEquipmentActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("您尚有未填的项目");
        }
    }

    /**
     * 获取留言
     *
     * @return
     */
    private String getTrafficText() {
        return getString(mEtRemark);
    }

    @Subscribe
    public void onEvent(SpinnerBean spinnerBean){
        if (spinnerBean.getSpinnerId()== TRAFFIC_TYPE){
            trafficType =spinnerBean.getId();
            mTvTraffic.setText(spinnerBean.getType());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.mSelectSpot = null;
        GlobalValue.mLineBeans = null;
        GlobalValue.mPropSelects=null;
        JsonUtils.reset();//释放json
        unregisterEventBus(this);
    }

    //公用初始化
    public void init() {
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(new Date());
        dayOfYear = currentDay.get(Calendar.DAY_OF_YEAR);
        year = currentDay.get(Calendar.YEAR);
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

    private void showTime(final TextView currentText) {
        if (pvTime == null) {
            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            //控制时间范围
            Calendar calendar = Calendar.getInstance();
            pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);//要在setTime 之前才有效果
            pvTime.setTime(new Date());
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        } else {
            pvTime.setTitle(currentText == mTvStartTime ? "开始时间" : "结束时间");
        }
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                checkTime(currentText, date);
            }


        });
        hideSoftWore(mEtRemark);
        pvTime.show();

    }

    /**
     * 检查日期是否合法
     *
     * @param currentText
     * @param date
     */
    public void checkTime(TextView currentText, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_YEAR) < dayOfYear && calendar.get(Calendar.YEAR) == year) {
            ToastUtils.showToast("对不起，您所选的时间已过期");
            return;
        }

        currentText.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        if (currentText == mTvStartTime) {
            startDate = date;
        } else {
            endDate = date;
        }
        if (mTvHowDay != null) {
            mTvHowDay.setText("共计" + CalendarUtils.getHowDayHowNight(startDate.getTime() + "", endDate.getTime() + ""));
        }

    }

    private boolean checkTimeAgain() {
        if (endDate.getTime() < startDate.getTime()) {
            ToastUtils.showToast("对不起，结束时间不能小于出发时间");
            return false;
        }

        String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
        if (Integer.parseInt(howDay) > 30) {
            ToastUtils.showToast("对不起，计划最长时间为30天，若有特殊需要请联系客服。");
            return false;
        }
        return true;
    }

}
