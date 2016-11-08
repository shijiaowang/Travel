package com.yunspeak.travel.ui.appoint.together;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherHolder extends BaseRecycleViewHolder<AppointTogetherBean.DataBean> {
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

    public AppointTogetherHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(final int position, final AppointTogetherBean.DataBean datas, final Context mContext) {
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
        String label = datas.getLabel();
        if (!StringUtils.isEmpty(label)) {
            String[] split = label.split(",");
            for (int i = 0; i < split.length; i++) {
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_appoint_title, mFlTitle, false);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_text);
                textView.setText(split[i]);
                mFlTitle.addView(inflate);
            }
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointTogetherDetailActivity.start(mContext,datas.getId());
            }
        });
          mTvIconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> end = MapUtils.Build().addKey().addUserId().addType(IVariable.PLAY_TOGETHER_CLICK_TYPE).addtId(datas.getId()).addRUserId(datas.getUser_id()).end();
                AppointTogetherEvent event=new AppointTogetherEvent();
                event.setClickPosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.APPOINT_CLICK_ZAN,end, IState.TYPE_LIKE,event);

            }
        });

    }
}
