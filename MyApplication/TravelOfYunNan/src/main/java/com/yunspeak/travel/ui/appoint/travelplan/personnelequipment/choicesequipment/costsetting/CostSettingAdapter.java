package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingAdapter extends BaseRecycleViewAdapter<BasecPriceBean> {
    public CostSettingAdapter(List<BasecPriceBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<BasecPriceBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CostSettingHolder(inflateView(R.layout.item_activity_cost_setting_price,parent));
    }
}
