package com.yunspeak.travel.ui.appoint.withme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/21 0021.
 * 找人带约伴
 */
public class AppointWithMeHolder extends BaseHolder<AppointWithMeBean.DataBean> {
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_dream) TextView mTvDream;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_money) TextView mTvMoney;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    public AppointWithMeHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMeBean.DataBean datas, Context mContext, int position) {
        mTvDream.setText("理想地:"+datas.getRoutes());
        mTvHaveNumber.setText("已有:"+datas.getNow_people()+"人");
        mTvMoney.setText("预算：¥"+datas.getTotal_price());
        FrescoUtils.displayRoundIcon(mIvBg,datas.getTravel_img());
        mTvIconLove.setTextColor((datas.getIs_like().equals("1")) ? mContext.getResources().getColor(R.color.colorff806d) : mContext.getResources().getColor(R.color.colorb5b5b5));
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "-" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time()+"000", datas.getEnd_time()+"000"));
        mTvLoveNumber.setText(datas.getCount_like());
        mTvWatchNumber.setText(datas.getBrowse());
        if (mFlTitle!=null && mFlTitle.getChildCount()>0){
            mFlTitle.removeAllViews();
        }
        String[] split = datas.getLabel().split(",");
        if (split==null || split.length==0)return;
        for (int i =  0; i < split.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(split[i]);
            mFlTitle.addView(textView);
        }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_appoint_play_with_me);
    }
}
