package com.example.administrator.travel.ui.adapter.holer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogether;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherHolder extends BaseHolder<AppointTogether.DataBean> {
    @BindView(R.id.fl_title) FlowLayout mFlTitle;
    @BindView(R.id.iv_icon) ImageView mIvIcon;
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
    public View root;

    public AppointTogetherHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(AppointTogether.DataBean datas, Context mContext, int position) {
        mTvMoney.setText(datas.getTotal_price());
        mTvTime.setText("行程日期: " + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "-" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        mTvIconLove.setTextColor((datas.getIs_like().equals("1")) ? mContext.getResources().getColor(R.color.colorff806d) : mContext.getResources().getColor(R.color.colorb5b5b5));
        mTvLoveNumber.setText(datas.getCount_like());
        mTvWatchNumber.setText(datas.getBrowse());
        if (mFlTitle != null && mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        x.image().bind(mIvIcon, datas.getTravel_img(), getImageOptions(DensityUtil.dip2px(112), DensityUtil.dip2px(112)));
        String[] split = datas.getLabel().split(",");
        for (int i = 0; i < split.length; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
            textView.setText(split[i]);
            mFlTitle.addView(textView);
        }
        List<AppointTogether.DataBean.RoutesBean> routes = datas.getRoutes();
        StringBuffer stringBuffer = new StringBuffer();
         if (routes!=null && routes.size()!=0) {
             for (AppointTogether.DataBean.RoutesBean bean : routes) {
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
        root = inflateView(R.layout.item_fragment_appoint_play_together);
        return root;
    }
}
