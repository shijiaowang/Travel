package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.common.util.DensityUtil;

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
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_price) TextView mTvPrice;
    //common start
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_line) TextView mTvLine;
    //Common end
    @BindView(R.id.tv_icon_love) FontsIconTextView mTvIconLove;
    @BindView(R.id.tv_watch_number) TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.tv_how_long) TextView mTvHowLong;
    @BindView(R.id.code) TextView mTvCode;

    @BindView(R.id.bv_enter_people)BadgeView mBvEnterPeople;
    @BindView(R.id.bv_bulletin_number)BadgeView mBvBulletinNumber;
    @BindView(R.id.tv_appointing)TextView mTvAppointing;
    @BindView(R.id.iv_delete) ImageView mIvDelete;
    @BindView(R.id.ll_code1) LinearLayout mLlCode1;
    @BindView(R.id.ll_code2) LinearLayout mLlCode2;
    @BindView(R.id.bt_pay) Button mBtPay;
    @BindView(R.id.bt_start) Button mBtStart;
    @BindView(R.id.bt_chat) Button mBtChat;
    @BindView(R.id.line) View mLine;
    @BindView(R.id.code2) TextView mTvCode2;
    @BindColor(R.color.colorff806d) @ColorInt int likeColor;
    @BindColor(R.color.colorb5b5b5) @ColorInt int normalColor;
    @BindColor(R.color.colorface04) @ColorInt int stateAppointing;
    @BindColor(R.color.color7fbdff) @ColorInt int stateNotDiscuss;//待评价
    @BindString(R.string.activity_circle_love_full) String fullLove;
    @BindString(R.string.activity_circle_love_empty) String emptyLove;
   private int desColor=stateAppointing;



    public MyAppointTogetherHolder(Context context) {
        super(context);
    }
    @Override
    protected void initItemDatas(Object datas1, Context mContext, int position) {
        if (datas1 instanceof MyAppointTogetherBean.DataBean) {
            int payStates=-1;
            MyAppointTogetherBean .DataBean datas = (MyAppointTogetherBean.DataBean) datas1;
            String isBoss = datas.getIs_boss();
            FrescoUtils.displayNormal(mIvIcon, datas.getTravel_img());
            mTvPrice.setText(datas.getTotal_price());
            mTvLoveNumber.setText(datas.getCount_like());
            mTvWatchNumber.setText(datas.getBrowse());
            mTvHaveNumber.setText("已有: " + datas.getNow_people() + "人");
            mTvPlanNumber.setText("计划: " + datas.getMax_people() + "人");
            mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
            mTvLine.setText(datas.getRoutes());
            mBvEnterPeople.setBadgeCount(datas.getNow_people());
            mBvBulletinNumber.setBadgeCount(datas.getBulletin());
            mTvHowLong.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd", datas.getAdd_time()));
            mTvAppointing.setText(getDesTextByState(datas.getState(),payStates));
            mTvAppointing.setTextColor(desColor);
            String isLike = datas.getIs_like();
            boolean equals;
            if (isLike == null) {
                equals = false;
            } else {
                equals = isLike.equals("1");
            }
            mTvIconLove.setTextColor(equals ? likeColor : normalColor);
            mTvIconLove.setText(equals ? fullLove : emptyLove);
            hideOrShow(datas, isBoss,payStates);
        }
    }

    private void hideOrShow(MyAppointTogetherBean.DataBean datas, String isBoss,int payStates) {
        if (payStates==1){//如果为1就显示支付
            mBtPay.setVisibility(View.VISIBLE);
            mLlCode1.setVisibility(View.GONE);
            mLlCode2.setVisibility(View.VISIBLE);
            mTvCode2.setText(datas.getId_code());
        }else {
            mBtPay.setVisibility(View.GONE);
            mLlCode2.setVisibility(View.GONE);
            mLlCode1.setVisibility(View.VISIBLE);
            mTvCode.setText(datas.getId_code());
        }

        if (isBoss.equals("1")){
            mBtStart.setVisibility(View.VISIBLE);
            if (payStates==3 || payStates==7) {
                mIvDelete.setVisibility(View.VISIBLE);
            }else {
                mIvDelete.setVisibility(View.GONE);
            }
            mBtPay.setVisibility(View.GONE);
            mLlCode2.setVisibility(View.GONE);
            mLlCode1.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLine.getLayoutParams();
            layoutParams.topMargin=0;
            mLine.setLayoutParams(layoutParams);

            mTvCode.setText(datas.getId_code());
        }else {
            mIvDelete.setVisibility(View.GONE);
            mBtStart.setVisibility(View.GONE);
            mBtPay.setVisibility(View.VISIBLE);
            mLlCode1.setVisibility(View.GONE);
            mLlCode2.setVisibility(View.VISIBLE);
            mTvCode2.setText(datas.getId_code());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLine.getLayoutParams();
            layoutParams.topMargin= DensityUtil.dip2px(25);
            mLine.setLayoutParams(layoutParams);
        }
        if (payStates==3 || (6<=payStates && payStates<=10) || isBoss.equals("1")){
            mBtChat.setVisibility(View.VISIBLE);
        }else {
            mBtChat.setVisibility(View.GONE);
        }
    }

    /**
     * 通过state获取描述字符串
     * @param state
     * @return
     */
  private String getDesTextByState(String state,int payStates){
      String desText="约伴订单";
      try {
          String[] stringArray = mContext.getResources().getStringArray(R.array.together_appoint);
          int i = Integer.parseInt(state)-1;
          payStates=i;
          switch (i){
              case 9:
                  desColor=stateNotDiscuss;
                  break;
              default:
                  desColor=stateAppointing;
                  break;
          }
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
