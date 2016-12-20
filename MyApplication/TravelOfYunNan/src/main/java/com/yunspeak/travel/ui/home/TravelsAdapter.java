package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomeBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wnagyang on 2016/7/7 0007.
 */
public class TravelsAdapter extends BaseRecycleViewAdapter<HomeBean.DataBean.FindTravelBean> {


    public TravelsAdapter(List<HomeBean.DataBean.FindTravelBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HomeBean.DataBean.FindTravelBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_travels, parent, false);
        return new TravelsHolder(inflate);
    }
}
