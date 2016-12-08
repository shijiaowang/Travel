package com.yunspeak.travel.ui.me.ordercenter.orders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail.OrdersDetailActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersHolder extends BaseRecycleViewHolder<MyOrdersBean.DataBean> {
    private final int currentType;
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_line) TextView mTvLine;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_status) TextView mTvStatus;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.b_pay) Button mBPay;
    @BindView(R.id.tv_cancel) TextView mTvCancel;
    @BindView(R.id.tv_id_number) TextView mTvIdNumber;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_total_price) TextView mTvTotalPrice;
    @BindView(R.id.tv_price) TextView mTvPrice;
    @BindView(R.id.tv_air) FontsIconTextView mTvAir;
    @BindView(R.id.tv_third) FontsIconTextView mTvThird;
    @BindString(R.string.activity_message_center_recommend) String fly;
    @BindString(R.string.fragment_play_with_me_air) String air;
    @BindString(R.string.fragment_play_with_me_add) String add;
    @BindString(R.string.fragment_find_active) String active;

    public MyOrdersHolder(View itemView, int currentType) {
        super(itemView);
        this.currentType = currentType;
    }





    @Override
    public void childBindView(final int position, final MyOrdersBean.DataBean datas, final Context mContext) {
        final int payType = datas.getPay_type();
        if (payType==2){
            mTvType.setText("活动订单");
            mTvType.setBackgroundResource(R.drawable.activity_orders_center02);
            mTvAir.setText(active);
            mTvThird.setText(fly);
            mTvStartAndLong.setText(datas.getMeet_address());
            mTvDayAndNight.setText( "至"+FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getEnd_time())+"结束");
        }else {
            mTvType.setText("约伴订单");
            mTvAir.setText(air);
            mTvThird.setText(add);
            mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
            mTvDayAndNight.setText(FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getEnd_time()));
        }
        FrescoUtils.displayRoundIcon(mIvIcon, datas.getTravel_img());
        mTvPlanNumber.setVisibility(View.GONE );
        mTvHaveNumber.setText("已有: " + datas.getPeople() + "人");
      mTvLine.setText(datas.getRoutes());
        mTvIdNumber.setText("订单号:"+datas.getOrder_sn());
        mTvTotalPrice.setText("合计:"+datas.getTotal_price());
        mTvPrice.setText("¥"+datas.getTotal_price());
        mTvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_DHms, datas.getAdd_time()));
        String status = datas.getStatus();
        mTvCancel.setTag(status);
        if (status.equals("1")){
            status="已支付";
          mBPay.setVisibility(View.GONE);
            mTvCancel.setText("删除");

        }else if (status.equals("2")){
            status="已取消";
            mBPay.setVisibility(View.GONE);
            mTvCancel.setText("删除");
        }else {
            status="未支付";
            mTvCancel.setVisibility(View.VISIBLE);
            mBPay.setBackgroundResource(R.drawable.activity_activate_detail_bg);
        }
        mTvStatus.setText(status);
        final String id = datas.getId();
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) mTvCancel.getTag();
                if (tag.equals("1")|| tag.equals("2")){
                    EnterAppointDialog.showCommonDialog(mContext, "删除订单记录", "确定", "删除订单后将无法查询到相关记录，是否删除？", new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            Map<String, String> orderMap = MapUtils.Build().addKey().addUserId().add("order_id",id).end();
                            MyOrdersEvent event = new MyOrdersEvent();
                            event.setPosition(position);
                            event.setOrderType(currentType);//页面状态
                            XEventUtils.postUseCommonBackJson(IVariable.DELETE_ORDER_HISTORY,orderMap,IState.TYPE_DELETE2, event);
                        }
                    });
                }else {
                    EnterAppointDialog.showCommonDialog(mContext,"取消订单", "确定","您是否要取消当前订单？" , new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            Map<String, String> deleteMap = MapUtils.Build().addKey().addUserId().addId(datas.getId()).end();
                            MyOrdersEvent event = new MyOrdersEvent();
                            event.setPosition(position);
                            event.setOrderType(currentType);
                            XEventUtils.postUseCommonBackJson(IVariable.CANCEL_ORDERS,deleteMap, IState.TYPE_DELETE, event);
                        }
                    });
                }

            }
        });


        mBPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, ConfirmOrdersActivity.class);
                intent.putExtra(IVariable.ID,id);
                intent.putExtra("pay_type",payType);
                mContext.startActivity(intent);

            }
        });
        final String routes = datas.getRoutes();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = datas.getId();
                Intent intent=new Intent(mContext, OrdersDetailActivity.class);
                intent.putExtra(IVariable.TYPE,payType);
                intent.putExtra(IVariable.NAME,routes);
                intent.putExtra(IVariable.ID,orderId);
                mContext.startActivity(intent);
            }
        });
    }
}
