package com.yunspeak.travel.ui.find.active;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class ActiveAdapter extends BaseRecycleViewAdapter<ActiveBean.DataBean> {

    public ActiveAdapter(List<ActiveBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<ActiveBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActiveHolder(inflateView(R.layout.item_activity_active,parent));
    }
}
