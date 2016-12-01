package com.yunspeak.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.easeui.EaseConstant;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.ui.me.myappoint.memberdetail.MemberDetailActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.view.BadgeView;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;

import java.util.Map;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 一起玩约伴订单
 */
public class MyAppointTogetherHolder extends BaseRecycleViewHolder<Object> {
    @BindView(R.id.rl_bulletin_board)
    RelativeLayout mRlBulletinBoard;
    @BindView(R.id.rl_member_detail)
    RelativeLayout mRlMemberDetail;
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    //common start
    @BindView(R.id.tv_have_number)
    TextView mTvHaveNumber;
    @BindView(R.id.tv_plan_number)
    TextView mTvPlanNumber;
    @BindView(R.id.tv_start_and_long)
    TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night)
    TextView mTvDayAndNight;
    @BindView(R.id.tv_line)
    TextView mTvLine;
    //Common end
    @BindView(R.id.tv_icon_love)
    FontsIconTextView mTvIconLove;
    @BindView(R.id.tv_watch_number)
    TextView mTvWatchNumber;
    @BindView(R.id.tv_love_number)
    TextView mTvLoveNumber;
    @BindView(R.id.tv_how_long)
    TextView mTvHowLong;
    @BindView(R.id.code)
    TextView mTvCode;

    @BindView(R.id.bv_enter_people)
    BadgeView mBvEnterPeople;
    @BindView(R.id.bv_bulletin_number)
    BadgeView mBvBulletinNumber;
    @BindView(R.id.tv_appointing)
    TextView mTvAppointing;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.bt_pay)
    Button mBtPay;
    @BindView(R.id.bt_start)
    Button mBtStart;
    @BindView(R.id.bt_chat)
    Button mBtChat;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.tv_icon_eye)
    FontsIconTextView mIconEye;
    /*   @BindView(R.id.ap_item)
       AppointItemView appointItemView;*/
    @BindView(R.id.code2)
    TextView mTvCode2;
    @BindColor(R.color.colorff806d)
    @ColorInt
    int likeColor;
    @BindColor(R.color.colorb5b5b5)
    @ColorInt
    int normalColor;
    @BindColor(R.color.colorface04)
    @ColorInt
    int stateAppointing;
    @BindColor(R.color.color7fbdff)
    @ColorInt
    int stateNotDiscuss;//待评价
    @BindString(R.string.activity_circle_love_full)
    String fullLove;
    @BindString(R.string.activity_circle_love_empty)
    String emptyLove;
    private int desColor = stateAppointing;
    protected int payStates = -1;
    private String title;
    private String content;
    private int showBoard;
    private int showStart;
    private int showDelete;
    private int showChat;
    private int showCode1;
    private int showCode2;
    private int showPay;

    public MyAppointTogetherHolder(View itemView) {
        super(itemView);
    }



    /**
     * 通过state获取描述字符串
     *
     * @param state
     * @return
     */
    private String getDesTextByState(String state, Context mContext) {
        String desText = "约伴订单";
        try {
            String[] stringArray = mContext.getResources().getStringArray(R.array.together_appoint);
            int i = Integer.parseInt(state);
            payStates = i;
            switch (i) {
                case 9:
                    desColor = stateNotDiscuss;
                    break;
                default:
                    desColor = stateAppointing;
                    break;
            }
            desText = stringArray[i];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desText;
    }

    @Override
    public void childBindView(final int position, Object datas1, final Context mContext) {
        if (datas1 instanceof MyAppointTogetherBean.DataBean) {
            payStates = -1;
            showBoard = View.GONE;
            showStart = View.GONE;
            showDelete = View.GONE;
            showChat = View.GONE;
            showCode1 = View.GONE;
            showCode2 = View.GONE;
            showPay = View.GONE;
            final MyAppointTogetherBean.DataBean datas = (MyAppointTogetherBean.DataBean) datas1;
            final int type = datas.getType();
            FrescoUtils.displayNormal(mIvIcon, datas.getTravel_img());
            mTvPrice.setText(IVariable.RMB + datas.getTotal_price());
            mTvHaveNumber.setText("已有: " + datas.getNow_people() + "人");
            mTvPlanNumber.setText("计划: " + datas.getMax_people() + "人");
            mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", datas.getEnd_time()));
            mTvLine.setText(datas.getRoutes());
            mBvEnterPeople.setBadgeCount(datas.getNow_people());
            mBvBulletinNumber.setBadgeCount(datas.getBulletin());
            mTvHowLong.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd", datas.getAdd_time()));
            mTvAppointing.setText(getDesTextByState(datas.getState(), mContext));
            mTvAppointing.setTextColor(desColor);
            final String id = datas.getId();
            dealPayState(payStates, datas);
            mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BulletinBoardActivity.class);
                    intent.putExtra(IVariable.DATA, id);
                    mContext.startActivity(intent);
                }
            });
            mBtChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(datas.getGroupid())) return;
                    ChatActivity.start(mContext, datas.getGroupid(), EaseConstant.CHATTYPE_GROUP);
                }
            });

            mIvDelete.setVisibility(showDelete);
            mRlBulletinBoard.setVisibility(showBoard);
            mTvCode.setVisibility(showCode1);
            mTvCode2.setVisibility(showCode2);
            mBtPay.setVisibility(showPay);
            mBtStart.setVisibility(showStart);
            mBtChat.setVisibility(showChat);
            mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterAppointDialog.showCommonDialog(mContext, "删除约伴", "确认", "注意！此操作将删除您的约伴！请慎重选择！", new ParentPopClick() {
                        @Override
                        public void onClick(int t) {
                            Map<String, String> deleteMap = MapUtils.Build().addKey().addUserId().addtId(datas.getId()).end();
                            MyAppointEvent myAppointEvent = new MyAppointEvent();
                            myAppointEvent.setPosition(position);
                            XEventUtils.postUseCommonBackJson(IVariable.DELETE_APPOINT, deleteMap, BaseToolBarActivity.TYPE_DELETE, myAppointEvent);
                        }
                    });
                }
            });

            mBtStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startBtn(datas, position, mContext);
                }
            });
            mBtPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderType = datas.getOrder_type();
                    if (StringUtils.isEmpty(orderType)) return;
                    Intent intent = new Intent(mContext, ConfirmOrdersActivity.class);
                    intent.putExtra("pay_type", orderType);
                    intent.putExtra(IVariable.ID, datas.getOrder_id());
                    mContext.startActivity(intent);
                }
            });

            mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MemberDetailActivity.class);
                    intent.putExtra(IVariable.DATA, id);
                    mContext.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 1) {
                        AppointTogetherDetailActivity.start(mContext, datas.getId());
                    } else {
                        ActivateDetailActivity.start(mContext, datas.getId());
                    }
                }
            });

        }
    }

    private void dealPayState(int payStates, MyAppointTogetherBean.DataBean datas) {
        String isBoos = datas.getIs_boss();
        int type = datas.getType();
        showCode2= View.GONE;
        showCode1=View.VISIBLE;
        showBoard=View.VISIBLE;
        switch (payStates) {
            case 1:
                showBoard=View.GONE;
                break;
            case 2:
                showCode2= View.VISIBLE;
                showCode1=View.GONE;
                showBoard = View.VISIBLE;
                showPay = View.VISIBLE;
                break;
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                showBoard = View.VISIBLE;
                showChat = View.VISIBLE;
                break;
        }
        if (type == 2) {
            showCode2= View.GONE;
            showCode1=View.GONE;
            showAppoint(false);
        } else {
            if (isBoos.equals("1")) {
                if (payStates == 3) {
                    mBtStart.setText("确认出发");
                    showDelete=View.VISIBLE;
                    showStart = View.VISIBLE;
                } else if (payStates == 7) {
                    mBtStart.setText("出发");
                    showDelete=View.VISIBLE;
                    showStart = View.VISIBLE;
                } else if (payStates == 8) {
                    mBtStart.setText("结束行程");
                    showStart = View.VISIBLE;
                }else if (payStates == 9) {
                    mBtStart.setText("评价");
                    showStart = View.VISIBLE;
                } else {
                    showStart = View.GONE;
                }
            } else {
                showDelete = View.GONE;
                if (payStates == 9) {
                    mBtStart.setText("评价");
                    showStart = View.VISIBLE;
                } else {
                    showStart = View.GONE;
                }
            }
            if (showCode2==View.VISIBLE){
                mTvCode2.setText("口令:" + datas.getId_code());
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLine.getLayoutParams();
                layoutParams.topMargin = DensityUtil.dip2px(25);
                mLine.setLayoutParams(layoutParams);
            }else {
                mTvCode.setText("口令:" + datas.getId_code());
            }
            showAppoint(true);
            String isLike = datas.getIs_like();
            boolean equals;
            if (isLike == null) {
                equals = false;
            } else {
                equals = isLike.equals("1");
            }
            mTvIconLove.setTextColor(equals ? likeColor : normalColor);
            mTvIconLove.setText(equals ? fullLove : emptyLove);
            mTvLoveNumber.setText(datas.getCount_like());
            mTvWatchNumber.setText(datas.getBrowse());
        }


}

    /**
     * 显示约伴特有的
     *
     * @param b
     */
    private void showAppoint(boolean b) {
        int showType = b ? View.VISIBLE : View.INVISIBLE;
        mTvLoveNumber.setVisibility(showType);
        mTvIconLove.setVisibility(showType);
        mTvWatchNumber.setVisibility(showType);
        mIconEye.setVisibility(showType);
    }

    private void startBtn(final MyAppointTogetherBean.DataBean datas, final int position, final Context mContext) {
        final String type = initType(payStates);
        if (StringUtils.isEmpty(type)) return;
        if (payStates == 9) {
            String content = "这真是一次不错的旅行啊";
            Map<String, String> discussMap = MapUtils.Build().addKey().addUserId().addtId(datas.getId()).addContent(content).end();
            MyAppointEvent myAppointEvent = new MyAppointEvent();
            myAppointEvent.setPosition(position);
            XEventUtils.postUseCommonBackJson(IVariable.DISCUSS_APPOINT, discussMap, BaseToolBarActivity.TYPE_DISCUSS, myAppointEvent);
        } else {
            EnterAppointDialog.showCommonDialog(mContext, title, "确定", content, new ParentPopClick() {
                @Override
                public void onClick(int t) {
                    changeAppoint(datas, position, mContext, type);
                }
            });
        }
    }

    private void changeAppoint(MyAppointTogetherBean.DataBean item1, int position, Context mContext, String type) {
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addtId(item1.getId()).addType(type).end();
        MyAppointEvent myAppointEvent = new MyAppointEvent();
        myAppointEvent.setPosition(position);
        myAppointEvent.setPayStatus(payStates);
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_APPOINT, end, BaseToolBarActivity.TYPE_CHANGE, myAppointEvent);
    }

    private String initType(int payStates) {
        switch (payStates) {
            case 3:
                title = "确认出发";
                content = "确认出发后将停止招募小伙伴。";
                return "1";
            case 7:
                title = "即刻启程";
                content = "开始您的约伴行程。";
                return "2";
            case 8:
                title = "完成约伴";
                content = "结束约伴行程。";
                return "3";
            case 9:
                return "9";
        }
        return "";
    }
}




