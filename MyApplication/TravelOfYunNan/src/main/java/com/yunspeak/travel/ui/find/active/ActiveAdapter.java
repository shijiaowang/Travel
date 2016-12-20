package com.yunspeak.travel.ui.find.active;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.bean.ActivityBean;

import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class ActiveAdapter extends BaseRecycleViewAdapter<ActivityBean> {

    public ActiveAdapter(List<ActivityBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<ActivityBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActiveHolder(inflateView(R.layout.item_activity_active,parent));
    }
}
