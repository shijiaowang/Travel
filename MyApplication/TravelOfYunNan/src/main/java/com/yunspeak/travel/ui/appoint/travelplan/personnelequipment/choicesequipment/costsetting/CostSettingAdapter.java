package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/11/15 0015.
 */

public class CostSettingAdapter extends BaseRecycleViewAdapter<CostSettingBean.DataBean> {
    public CostSettingAdapter(List<CostSettingBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<CostSettingBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CostSettingHolder(inflateView(R.layout.item_activity_cost_setting_price,parent));
    }
}
