package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingHolder extends BaseRecycleViewHolder<CostSettingBean.DataBean> {
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_money) TextView tvMoney;
    public CostSettingHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, CostSettingBean.DataBean data, Context mContext) {
           tvType.setText(data.getType());
        tvMoney.setText(data.getValue()+"元/日");
    }
}
