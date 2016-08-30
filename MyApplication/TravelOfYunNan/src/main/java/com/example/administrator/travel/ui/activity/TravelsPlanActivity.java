package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.travel.R;

import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private TextView mTvRightNext;
    private TimePickerView pvTime;
    private Date startDate=new Date();
    private Date endDate=new Date();

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan;
    }

    @Override
    protected void initEvent() {
        init();
        mBtSelectLine.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
    }

    private void init() {
        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));
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
                startActivity(new Intent(this, LinePlanActivity.class));
                break;
            case R.id.tv_start:
                showTime(mTvStartTime);
                break;
            case R.id.tv_end:
                showTime(mTvEndTime);
                break;
            case R.id.bt_next:
                startActivity(new Intent(this,PersonnelEquipmentActivity.class));
                break;
        }
    }


    private void showTime(final TextView textView) {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setTitle("出发时间");
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
                if (textView==mTvStartTime){
                    startDate=date;
                }else {
                    endDate=date;
                }
                //计算几天几夜
                howDay();
            }
        });
        pvTime.show();

    }

    /**
     * 计算一个用了几天几晚m
     */
    private void howDay() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (pvTime!=null && pvTime.isShowing()){
                pvTime.dismiss();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
