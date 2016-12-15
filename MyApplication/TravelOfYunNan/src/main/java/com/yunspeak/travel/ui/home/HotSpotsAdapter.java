package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 */
public class HotSpotsAdapter extends BaseRecycleViewAdapter<HomeBean.DataBean.DestinationBean> {


    public HotSpotsAdapter(List<HomeBean.DataBean.DestinationBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_hot_spots, parent, false);
        return new HotSpotsHolder(inflate);
    }


}
