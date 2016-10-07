package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.x;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 一起玩约伴订单
 */
public class MyAppointTogetherHolder extends BaseHolder<Object> {
    @BindView(R.id.rl_bulletin_board)  RelativeLayout mRlBulletinBoard;
    @BindView(R.id.rl_member_detail)  RelativeLayout mRlMemberDetail;
    @BindView(R.id.iv_icon) ImageView mIvIcon;
    @BindView(R.id.tv_price) TextView mTvPrice;
    @BindView(R.id.tv_line) TextView mTvLine;
    @BindView(R.id.tv_delete) TextView mTvDelete;
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    @BindView(R.id.code) TextView mTvCode;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    @BindView(R.id.bv_enter_people)BadgeView mBvEnterPeople;
    @BindView(R.id.bv_bulletin_number)BadgeView mBvBulletinNumber;
    @BindView(R.id.tv_appointing)TextView mTvAppointing;
    @BindColor(R.color.colorff806d) @ColorInt int likeColor;
    @BindColor(R.color.colorb5b5b5) @ColorInt int normalColor;
    @BindString(R.string.activity_circle_love_full) String fullLove;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;


    public MyAppointTogetherHolder(Context context) {
        super(context);
    }
    @Override
    protected void initItemDatas(Object datas1, Context mContext, int position) {
        if (datas1 instanceof MyAppointTogetherBean.DataBean) {
            MyAppointTogetherBean .DataBean datas = (MyAppointTogetherBean.DataBean) datas1;
            x.image().bind(mIvIcon, datas.getTravel_img());
            mTvPrice.setText(datas.getTotal_price());
            mTvLoveNumber.setText(datas.getCount_like());
            mTvWatchNumber.setText(datas.getBrowse());
            mTvHaveNumber.setText("已有: " + datas.getNow_people() + "人");
            mTvPlanNumber.setText("计划: " + datas.getMax_people() + "人");
            mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
            mBvEnterPeople.setBadgeCount(datas.getNow_people());
            mBvBulletinNumber.setBadgeCount(datas.getBulletin());
            mTvCode.setText(datas.getId_code());
            mTvHowLong.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd", datas.getAdd_time()));
            mTvLine.setText(datas.getRoutes());
            mTvAppointing.setText(getDesTextByState(datas.getState()));
            String isLike = datas.getIs_like();
            boolean equals;
            if (isLike == null) {
                equals = false;
            } else {
                equals = isLike.equals("1");
            }
            mTvIconLove.setTextColor(equals ? likeColor : normalColor);
            mTvIconLove.setText(equals ? fullLove : emptyLove);
        }
    }

    /**
     * 通过state获取描述字符串
     * @param state
     * @return
     */
  private String getDesTextByState(String state){
      String desText="约伴订单";
      try {
          String[] stringArray = mContext.getResources().getStringArray(R.array.together_appoint);
          int i = Integer.parseInt(state);
          desText=stringArray[i];
      } catch (Exception e) {
          e.printStackTrace();
      }
      return desText;
  }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_my_appoint_together);
    }
}
