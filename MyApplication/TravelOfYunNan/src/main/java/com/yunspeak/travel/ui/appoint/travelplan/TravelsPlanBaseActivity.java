package com.yunspeak.travel.ui.appoint.travelplan;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.TimePickerView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.PersonnelEquipmentActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.PersonnelEquipmentWithMeActivity;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.view.GradientTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.JsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/22.
 */
public abstract class TravelsPlanBaseActivity extends BaseCutPhotoActivity implements View.OnClickListener {
    @BindView(R.id.vs_content) ViewStub mVsContent;
    @BindView(R.id.bt_next) Button mBtNext;
    @BindView(R.id.tv_start) TextView mTvStart;
    @BindView(R.id.tv_end) TextView mTvEnd;
    @BindView(R.id.tv_end_time) TextView mTvEndTime;
    @BindView(R.id.tv_start_time) TextView mTvStartTime;
    @BindView(R.id.tv_how_day) TextView mTvHowDay;
    @BindView(R.id.tv_start_morning) GradientTextView mTvStartMorning;
    @BindView(R.id.tv_start_night) GradientTextView mTvStartNight;
    @BindView(R.id.tv_end_morning) GradientTextView mTvEndMorning;
    @BindView(R.id.tv_end_night) GradientTextView mTvEndNight;
    @BindView(R.id.rl_start_morning) RelativeLayout mRlStartMorning;
    @BindView(R.id.rl_start_night) RelativeLayout mRlStartNight;
    @BindView(R.id.rl_end_morning) RelativeLayout mRlEndMorning;
    @BindView(R.id.rl_end_night) RelativeLayout mRlEndNight;
    @BindView(R.id.tv_icon) TextView mTvIcon;
    @BindView(R.id.rl_icon) RelativeLayout mRlIcon;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_delete) TextView mTvDelete;
    private TimePickerView pvTime;
    protected Date startDate;
    protected Date endDate;
    protected int dayOfYear;
    protected int year;
    protected Date endLine;
    protected Date startLine;
    private int howDay;


    /**
     * 是否隐藏右边
     * @return
     */
    protected abstract boolean isHideRight();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_travels_plan_base;
    }

    @Override
    protected void onSuccess(HttpEvent o) {

    }

    @Override
    protected String initTitle() {
        return "旅行计划";
    }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }
    @Override
    protected void initEvent() {
        init();
        mTvIcon.setOnClickListener(this);
        mBtNext.setOnClickListener(this);
        if(isHideRight()){
            mRlStartMorning.setVisibility(View.GONE);
            mRlEndMorning.setVisibility(View.GONE);
            mRlStartNight.setVisibility(View.GONE);
            mRlEndNight.setVisibility(View.GONE);
        }else {
            mRlStartMorning.setOnClickListener(this);
            mRlEndMorning.setOnClickListener(this);
            mRlStartNight.setOnClickListener(this);
            mRlEndNight.setOnClickListener(this);
        }
        mTvStart.setOnClickListener(this);
        mTvEnd.setOnClickListener(this);
        mVsContent.setLayoutResource(initChildLayoutRes());
        mVsContent.inflate();
        initChildEvent();
    }


    private void init() {
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(new Date());
        dayOfYear = currentDay.get(Calendar.DAY_OF_YEAR);
        year = currentDay.get(Calendar.YEAR);
        startDate = new Date();
        endDate = new Date();
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));
        if (mTvHowDay != null) {
            mTvHowDay.setText("共计" + CalendarUtils.getHowDayHowNight(startDate.getTime() + "", endDate.getTime() + ""));
        }
        howDay = CalendarUtils.getHowDay(startDate.getTime()+"",endDate.getTime()+"");
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected String initUrl() {
        return null;
    }


    protected abstract int initChildLayoutRes();

    protected abstract void initChildEvent();


    @Override
    protected String initRightText() {
        return "下一步";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        super.otherOptionsItemSelected(item);
        onClick(mBtNext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_icon:
                showPictureCutPop(mBtNext);
                break;
            case R.id.tv_start:
                showTime(mTvStartTime);
                break;
            case R.id.tv_end:
                showTime(mTvEndTime);
                break;
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
            default:
                childClick(v);
                break;
        }
    }

    protected abstract void childClick(View v);




    protected void showTime(final TextView currentText) {
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
        if (!isHideRight()){
            hideSoftWore(getEdView());
        }
        pvTime.show();
    }

    protected abstract EditText getEdView();

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
        howDay = CalendarUtils.getHowDay(startDate.getTime()+"",endDate.getTime()+"");

    }

    /**
     * 再次检查时间
     * @return
     */
    protected boolean checkTimeAgain() {
        if (endDate.getTime() < startDate.getTime()) {
            ToastUtils.showToast("对不起，结束时间不能小于出发时间");
            return false;
        }

        int howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
        if (howDay > 30) {
            ToastUtils.showToast("对不起，计划最长时间为30天，若有特殊需要请联系客服。");
            return false;
        }
        return true;
    }

    /**
     * 添加json
     */
    private void addJson() {
        try {
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            JsonUtils.putString(IVariable.USER_ID, GlobalUtils.getUserInfo().getId(), basecJsonObject);
            JsonUtils.putString(IVariable.START_TIME, startDate.getTime() / 1000 + "", basecJsonObject);
            JsonUtils.putString(IVariable.END_TIME, endDate.getTime() / 1000 + "", basecJsonObject);
            addChildJson(basecJsonObject);
            Class c=isHideRight()? PersonnelEquipmentWithMeActivity.class:PersonnelEquipmentActivity.class;
            Intent intent = new Intent(this, c);
            intent.putExtra(IVariable.PAGE_SIZE,howDay);
            GlobalValue.mFileName=filename;
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast("您尚有未填的项目");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (pvTime != null && pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void childDisplay(String url, String filename) {
        mRlIcon.setVisibility(View.VISIBLE);
        mTvIcon.setVisibility(View.GONE);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRlIcon.setVisibility(View.GONE);
                mTvIcon.setVisibility(View.VISIBLE);
            }
        });
        FrescoUtils.displayRoundIcon(mIvIcon,url,300,300);
    }

    protected abstract void addChildJson(JSONObject basecJsonObject) throws Exception;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValue.mSelectSpot = null;
        GlobalValue.mLineBeans = null;
        GlobalValue.mPropSelects=null;
        GlobalValue.mFileName=null;
        JsonUtils.reset();//释放json
    }
}