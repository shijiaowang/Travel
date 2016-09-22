package com.example.administrator.travel.ui.appoint.travelplan;

import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.view.GradientTextView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/9/22.
 */
public abstract class TravelsPlanBaseActivity extends BarBaseActivity {
    @ViewInject(R.id.vs_content)
    private ViewStub mVsContent;
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

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan_base;
    }

    @Override
    protected void initEvent() {
        initChildLayoutRes();
        initChildEvent();
    }

    protected abstract void initChildLayoutRes();

    protected abstract void initChildEvent();


    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return null;
    }
}
