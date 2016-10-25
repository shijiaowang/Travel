package com.yunspeak.travel.ui.appoint.together;


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

import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherHolder extends BaseHolder<AppointTogetherBean.DataBean> {
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_add) TextView mTvAdd;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_money) TextView mTvMoney;
    @BindView(R.id.tv_start_and_time) TextView mTvStartAndTime;
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    private LayoutInflater inflater;


    public AppointTogetherHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointTogetherBean.DataBean datas, Context mContext, int position) {
        mTvMoney.setText(datas.getTotal_price());
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "-" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        mTvIconLove.setTextColor((datas.getIs_like().equals("1")) ? mContext.getResources().getColor(R.color.colorff806d) : mContext.getResources().getColor(R.color.colorb5b5b5));
        mTvIconLove.setText((datas.getIs_like().equals("1")) ? R.string.activity_circle_love_full:R.string.activity_circle_love_empty);
        mTvLoveNumber.setText(datas.getCount_like());
        mTvWatchNumber.setText(datas.getBrowse());
        if (mFlTitle != null && mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        FrescoUtils.displayRoundIcon(mIvIcon,datas.getTravel_img());
        String[] split = datas.getLabel().split(",");
        for (int i = 0; i < split.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(split[i]);
            mFlTitle.addView(textView);
        }
        List<AppointTogetherBean.DataBean.RoutesBean> routes = datas.getRoutes();
        StringBuffer stringBuffer = new StringBuffer();
         if (routes!=null && routes.size()!=0) {
             for (AppointTogetherBean.DataBean.RoutesBean bean : routes) {
                 stringBuffer.append(bean.getTitle() + "-");
             }
             String add = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
             mTvAdd.setText(add);
         }
        mTvHaveNumber.setText("已有: "+datas.getNow_people()+"人");
        mTvPlanNumber.setText("计划: "+datas.getMax_people()+"人");
        mTvStartAndTime.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time()+"000",datas.getEnd_time()+"000"));


    }

    @Override
    public View initRootView(Context mContext) {
        inflater = LayoutInflater.from(mContext);
        return inflateView(R.layout.item_fragment_appoint_play_together);
    }
}