package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.appoint.together.AppointTogetherBean;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 约伴中
 */
public class MyAppointingHolder extends BaseHolder<MyAppointBean.DataBean.TravelPlanBean> {
    @BindView(R.id.iv_icon) ImageView mIvIcon;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_price) TextView mTvPrice;
    @BindView(R.id.tv_delete) TextView mTvDelete;
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
    public MyAppointingHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(MyAppointBean.DataBean.TravelPlanBean datas, Context mContext, int position) {
        x.image().bind(mIvIcon,datas.getTravel_img());
        mTvPrice.setText(datas.getTotal_price());
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "-" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        //mTvIconLove.setTextColor((datas.get.equals("1")) ? mContext.getResources().getColor(R.color.colorff806d) : mContext.getResources().getColor(R.color.colorb5b5b5));
        //mTvIconLove.setText((datas.getIs_like().equals("1")) ? R.string.activity_circle_love_full:R.string.activity_circle_love_empty);
        //mTvLoveNumber.setText(datas.get());
        mTvWatchNumber.setText(datas.getBrowse());
        mTvHaveNumber.setText("已有: "+datas.getUser_count()+"人");
        //mTvPlanNumber.setText("计划: "+datas.getMax_people()+"人");
        mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time()+"000",datas.getEnd_time()+"000"));
        mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        // mTvHaveNumber.setText("已有："+data.get()+"人");
        String currentTime=new Date().getTime()+"";
        mTvSurplusDay.setText("剩余："+CalendarUtils.getHowDay(currentTime,datas.getEnd_time()+"000")+"天");
        mTvPrice.setText("¥"+datas.getTotal_price());
        mTvEndDay.setText("招募截止日期："+FormatDateUtils.FormatLongTime("yyyy-MM-dd",datas.getEnd_time()));

    }



    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_my_appointting);
    }
}
