package com.yunspeak.travel.ui.me.ordercenter.orders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/11 0011.
 */
public class MyOrdersHolder extends BaseHolder<MyOrdersBean.DataBean> {
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
    public MyOrdersHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(MyOrdersBean.DataBean datas, Context mContext, int position) {
        FrescoUtils.displayRoundIcon(mIvIcon, datas.getTravel_img());
        mTvPlanNumber.setVisibility(View.GONE );
        mTvHaveNumber.setText("已有: " + datas.getPeople() + "人");
        mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
        mTvDayAndNight.setText(FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getEnd_time()));
        mTvLine.setText(datas.getRoutes());
        mTvIdNumber.setText("订单号:"+datas.getOrder_sn());
        mTvTotalPrice.setText("合计:"+datas.getTotal_price());
        mTvPrice.setText("¥"+datas.getTotal_price());
        mTvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_DHms, datas.getAdd_time()));
        String status = datas.getStatus();
        if (status.equals("1")){
            status="已支付";
            mBPay.setVisibility(View.GONE);
            mTvCancel.setVisibility(View.VISIBLE);
        }else if (status.equals("2")){
            status="已取消";
            mBPay.setVisibility(View.GONE);
            mTvCancel.setVisibility(View.GONE);
        }else {
            status="未支付";
            mBPay.setVisibility(View.VISIBLE);
            mTvCancel.setVisibility(View.VISIBLE);
        }
        mTvStatus.setText(status);
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_orders);
    }
}
