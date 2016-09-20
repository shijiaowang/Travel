package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMeHolder extends BaseHolder<AppointWithMe.DataBean> {
    @ViewInject(R.id.fl_title)
    private FlowLayout mFlTitle;
    @ViewInject(R.id.tv_icon_love)
    private FontsIconTextView mTvIconLove;
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_dream)
    private TextView mTvDream;
    @ViewInject(R.id.tv_have_number)
    private TextView mTvHaveNumber;
    @ViewInject(R.id.tv_money)
    private TextView mTvMoney;
    @ViewInject(R.id.tv_watch_number)
    private TextView mTvWatchNumber;
    @ViewInject(R.id.tv_love_number)
    private TextView mTvLoveNumber;
    @ViewInject(R.id.tv_how_long)
    private TextView mTvHowLong;
    @ViewInject(R.id.tv_start_and_long)
    private TextView mTvStartAndLong;
    public AppointWithMeHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointWithMe.DataBean datas, Context mContext, int position) {
        mTvDream.setText("理想地:"+datas.getRoutes());
        mTvHaveNumber.setText("已有:"+datas.getNow_people()+"人");
        mTvMoney.setText("预算：¥"+datas.getTotal_price());
        x.image().bind(mIvBg, datas.getTravel_img(), getImageOptions(DensityUtil.dip2px(112), DensityUtil.dip2px(112)));
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
        View inflate =inflateView(R.layout.item_fragment_appoint_play_with_me);
        return inflate;
    }
}
