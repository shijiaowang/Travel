package com.example.administrator.travel.ui.appoint.travelplan;


import android.content.Intent;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentActivity;
import com.example.administrator.travel.ui.appoint.personnelequipment.PersonnelEquipmentWithMeActivity;
import com.example.administrator.travel.ui.baseui.BaseCropPhotoActivity;
import com.example.administrator.travel.ui.view.GradientTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.JsonUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by android on 2016/9/22.
 */
public abstract class TravelsPlanBaseActivity extends BaseCropPhotoActivity implements View.OnClickListener {
    private TextView mTvRightNext;
    @ViewInject(R.id.vs_content)
    private ViewStub mVsContent;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_start)
    private TextView mTvStart;
    @ViewInject(R.id.tv_end)
    private TextView mTvEnd;
    @ViewInject(R.id.tv_end_time)
    private TextView mTvEndTime;
    @ViewInject(R.id.tv_start_time)
    private TextView mTvStartTime;
    @ViewInject(R.id.tv_how_day)
    private TextView mTvHowDay;
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
    @ViewInject(R.id.tv_icon)
    private TextView mTvIcon;
    @ViewInject(R.id.rl_icon)
    private RelativeLayout mRlIcon;
    @ViewInject(R.id.iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;
    private TimePickerView pvTime;
    protected Date startDate = new Date();
    protected Date endDate = new Date();
    protected int dayOfYear;
    protected int year;
    protected Date endLine;
    protected Date startLine;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan_base;
    }

    /**
     * 是否隐藏右边
     * @return
     */
    protected abstract boolean isHideRight();

    @Override
    protected void initChildListener() {
        init();
        mTvIcon.setOnClickListener(this);
        mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mTvRightNext.setOnClickListener(this);
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
        x.view().inject(this);
        initChildEvent();

    }

    private void init() {
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(new Date());
        dayOfYear = currentDay.get(Calendar.DAY_OF_YEAR);
        year = currentDay.get(Calendar.YEAR);
        mTvStartTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        mTvEndTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

    @Override
    protected void onLoad() {
          setIsProgress(false);
    }

    protected abstract int initChildLayoutRes();

    protected abstract void initChildEvent();



    @Override
    protected String setTitleName() {
        return "旅行计划";
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
            default:
                childClick(v);
                break;
        }
    }

    protected abstract void childClick(View v);
    @Override
    protected ImageView childViewShow() {
        mRlIcon.setVisibility(View.VISIBLE);
        mTvIcon.setVisibility(View.GONE);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRlIcon.setVisibility(View.GONE);
                mTvIcon.setVisibility(View.VISIBLE);
            }
        });
        return mIvIcon;
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }


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

        String howDay = CalendarUtils.getHowDay(startDate.getTime() + "", endDate.getTime() + "");
        if (Integer.parseInt(howDay) > 30) {
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
            if (StringUtils.isEmpty(filename)){
                throw new Exception();
            }
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
