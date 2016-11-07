package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/7 0007.
 */

public class MyCreateAppointHolder extends BaseRecycleViewHolder<MyCreateAppointBean.DataBean> {
    @BindView(R.id.iv_appoint_bg)
    SimpleDraweeView ivAppointBg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_select)
    FontsIconTextView tvSelect;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    public MyCreateAppointHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, MyCreateAppointBean.DataBean data, Context mContext) {
        FrescoUtils.displayNormal(ivAppointBg,data.getTravel_img());
        tvTime.setText("行程日期:"+FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getStart_time())+"-"+FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getEnd_time()));
        tvCreateTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm",data.getAdd_time()));
        tvTitle.setText(data.getRoutes());
        tvMoney.setText("¥"+data.getTotal_price());
        if (data.getIs_push()==1){
             itemView.setAlpha(0.4f);
        }else {
             itemView.setAlpha(1.0f);
        }
        if (position== EnterAppointDialog.select){
            tvSelect.setVisibility(View.VISIBLE);
        }else {
            tvSelect.setVisibility(View.GONE);
        }
    }
}
