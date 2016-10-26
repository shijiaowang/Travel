package com.yunspeak.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWithMeSelectActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 约伴中
 */
public class MyAppointingWithMeHolder extends BaseRecycleViewHolder<Object> {
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_price) TextView mTvPrice;
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    @BindView(R.id.tv_end_day)TextView mTvEndDay;
    @BindView(R.id.tv_surplus_day)TextView mTvSurplusDay;
    @BindView(R.id.tv_enter_people)TextView mTvEnterPeolple;
    @BindView(R.id.tv_line)TextView mTvLine;
    @BindView(R.id.tv_appointing)TextView mTvAppoint;
    @BindView(R.id.ll_enter)LinearLayout mLlEnter;
    @BindColor(R.color.colorff806d) @ColorInt int likeColor;
    @BindColor(R.color.colorb5b5b5) @ColorInt int normalColor;
    @BindString(R.string.activity_circle_love_full) String fullLove;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;

    public MyAppointingWithMeHolder(View itemView) {
        super(itemView);
    }


    private String getDesTextByState(String state,Context mContext) {
        String des="约伴订单";
        try {
            String[] stringArray = mContext.getResources().getStringArray(R.array.with_me_appoint);
            int i = Integer.parseInt(state);
            des=stringArray[i];
        } catch (Exception e) {
              e.printStackTrace();
        }
        return des;
    }



    @Override
    public void childBindView(int position, Object datas1, final Context mContext) {
        if (datas1 instanceof MyAppointWithMeBean.DataBean) {
            final MyAppointWithMeBean.DataBean datas = (MyAppointWithMeBean.DataBean) datas1;
            FrescoUtils.displayNormal(mIvIcon,datas.getTravel_img());
            mTvPrice.setText(datas.getTotal_price());
            mTvLoveNumber.setText(datas.getCount_like());
            mTvWatchNumber.setText(datas.getBrowse());
            mTvPlanNumber.setVisibility(View.GONE);
            mTvHaveNumber.setText("已有: " + datas.getUser_count() + "人");
            mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
            mTvEnterPeolple.setText("("+datas.getUser_count()+")");
            String currentTime = new Date().getTime() + "";
            mTvSurplusDay.setText("剩余：" + CalendarUtils.getHowDay(currentTime, datas.getEnd_time() + "000") + "天");
            mTvPrice.setText("¥" + datas.getTotal_price());
            mTvEndDay.setText("招募截止日期：" + FormatDateUtils.FormatLongTime("yyyy-MM-dd", datas.getEnd_time()));
            mTvHowLong.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd", datas.getAdd_time()));
            mTvLine.setText(datas.getRoutes());
            mTvAppoint.setText(getDesTextByState(datas.getState(),mContext));
            String isLike = datas.getIs_like();
            boolean equals;
            if (isLike == null) {
                equals = false;
            } else {
                equals = isLike.equals("1");
            }
            mTvIconLove.setTextColor(equals ? likeColor : normalColor);
            mTvIconLove.setText(equals ? fullLove : emptyLove);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,AppointWithMeDetailActivity.class);
                    intent.putExtra(IVariable.TID,datas.getId());
                    mContext.startActivity(intent);
                }
            });
            mLlEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,MyWithMeSelectActivity.class);
                        intent.putExtra(IVariable.ID, datas.getId());
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

