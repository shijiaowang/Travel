package com.yunspeak.travel.ui.appoint.withme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.together.AppointTogetherEvent;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.view.FlowLayout;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/21 0021.
 * 找人带约伴
 */
public class AppointWithMeHolder extends BaseRecycleViewHolder {
    @BindView(R.id.fl_title)
    FlowLayout mFlTitle;
    @BindView(R.id.tv_icon_love)
    FontsIconTextView mTvIconLove;
    @BindView(R.id.iv_bg)
    SimpleDraweeView mIvBg;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_dream) TextView mTvDream;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_money) TextView mTvMoney;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    @BindView(R.id.tv_start_and_long) TextView mTvStartAndLong;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;
    @BindString(R.string.activity_circle_love_full) String fullLove;
    @BindColor(R.color.otherFf7f6c) @ColorInt
    int color1;
    @BindColor(R.color.otherTitleBg) @ColorInt int color2;
    @BindColor(R.color.other74b8ff) @ColorInt int color3;
    @BindColor(R.color.otherFcae04) @ColorInt int color4;
    @BindColor(R.color.other9f8fe4) @ColorInt int color5;
    @BindColor(R.color.other50c3eb) @ColorInt int color6;
    @BindColor(R.color.other5ee5c5) @ColorInt int color7;
    private int [] colors=new int[]{color1,color2,color3,color4,color5,color6,color7};
    private int [] titleBgs=new int[]{R.drawable.fragment_appoint_title1_bg,R.drawable.fragment_appoint_title2_bg,R.drawable.fragment_appoint_title3_bg,R.drawable.fragment_appoint_title4_bg,R.drawable.fragment_appoint_title5_bg,R.drawable.fragment_appoint_title6_bg,R.drawable.fragment_appoint_title7_bg,};
    public AppointWithMeHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(final int position, final Object data, final Context mContext) {
        final AppointWithMeBean.DataBean datas = (AppointWithMeBean.DataBean) data;
        mTvDream.setText("理想地:" + datas.getRoutes());
        mTvHaveNumber.setText("已有:" + datas.getNow_people() + "人");
        mTvMoney.setText("预算：¥" + datas.getTotal_price());
        FrescoUtils.displayRoundIcon(mIvBg, datas.getTravel_img());
        mTvIconLove.setText(datas.getIs_like().equals("1")?fullLove:emptyLove);
        mTvIconLove.setTextColor((datas.getIs_like().equals("1")) ? mContext.getResources().getColor(R.color.colorff806d) : mContext.getResources().getColor(R.color.colorb5b5b5));
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "-" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
        mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
        mTvHowLong.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss",datas.getAdd_time()));
        mTvLoveNumber.setText(datas.getCount_like());
        mTvWatchNumber.setText(datas.getBrowse());
        if (mFlTitle != null && mFlTitle.getChildCount() > 0) {
            mFlTitle.removeAllViews();
        }
        String label = datas.getLabel();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointWithMeDetailActivity.start(mContext, datas.getId());
            }
        });
        mTvIconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> end = MapUtils.Build().addKey().addUserId().addType(IVariable.WITH_ME_CLICK_TYPE).addtId(datas.getId()).addRUserId(datas.getUser_id()).end();
                AppointWithMeEvent event=new AppointWithMeEvent();
                event.setPosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.APPOINT_CLICK_ZAN,end, IState.TYPE_LIKE,event);

            }
        });
        if (!StringUtils.isEmpty(label)) {
            String[] split = label.split(",");
            for (int i = 0; i < split.length; i++) {
                View view = View.inflate(mContext, R.layout.item_fragment_appoint_title, mFlTitle);
                TextView textView = (TextView) view.findViewById(R.id.tv_text);
                textView.setText(split[i]);
                textView.setBackgroundResource(titleBgs[i%titleBgs.length]);
                textView.setTextColor(colors[i%colors.length]);
                mFlTitle.addView(view);
            }
        }
    }
}
