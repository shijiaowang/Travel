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
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_money) TextView tvMoney;
    private String des;

    public CostSettingHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, BasecPriceBean data, Context mContext) {
           tvType.setText(data.getKey());
        des = data.getValue();
        switch (data.getType()){
            case "1":
                des+="元/日";
                break;
            case "2":
                des+="元/次";
                break;
            case "3":
                des+="元/周期";
                break;
            default:
                des ="错误数据！谨慎！";
                break;
        }
        tvMoney.setText(des);
    }
}
