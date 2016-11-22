package com.yunspeak.travel.ui.find.findcommon;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/30.
 */
public class DestinationAdapter extends BaseRecycleViewAdapter<DestinationBean.DataBean.BodyBean> {


    private final boolean isDestination;

    public DestinationAdapter(List<DestinationBean.DataBean.BodyBean> mDatas, Context mContext, boolean isDestination) {
        super(mDatas, mContext);
        this.isDestination = isDestination;

    }

    @Override
    public BaseRecycleViewHolder<DestinationBean.DataBean.BodyBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DestinationHoler(inflateView(R.layout.item_activity_destination,parent),isDestination);
    }
}
