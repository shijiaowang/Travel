package com.yunspeak.travel.ui.find.travels;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindTravelsBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class TravelsAdapter extends BaseRecycleViewAdapter<FindTravelsBean.DataBean> {


    public TravelsAdapter(List<FindTravelsBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<FindTravelsBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TravelsHolder(inflateView(R.layout.item_activity_travels,parent));
    }
}
