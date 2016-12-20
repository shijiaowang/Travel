package com.yunspeak.travel.ui.me.fansandfollow;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.bean.Follow;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;


import java.util.List;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class FanAdapter extends BaseRecycleViewAdapter<Follow> {


    public FanAdapter(List<Follow> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<Follow> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FanHolder(inflateView(R.layout.item_activity_follow_and_fan,parent),mDatas);
    }
}
