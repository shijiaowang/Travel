package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingHolder extends BaseRecycleViewHolder<BasecPriceBean> {
    private final int day;
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_money) TextView tvMoney;
    @BindView(R.id.tv_number) TextView tvNumber;

    public CostSettingHolder(View itemView, int day) {
        super(itemView);
        this.day = day;
    }

    @Override
    public void childBindView(int position, BasecPriceBean data, Context mContext) {
           tvType.setText(data.getKey());
        String des = data.getValue();
        switch (data.getType()){
            case "1":
                des +="元/日";
                tvNumber.setText("x"+day);
                break;
            case "2":
                des +="元/次";
                tvNumber.setText("x"+1);
                break;
            case "3":
                des +="元/周期";
                int number=day/7;
                number=number<1?1:number;
                tvNumber.setText("x"+number);
                break;
            default:
                des ="错误数据！谨慎！";
                tvNumber.setText("");
                break;
        }
        tvMoney.setText(des);
    }
}
