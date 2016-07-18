package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.FollowAndFan;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.FollowAndFanHolder;


import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FollowAndFanAdapter extends TravelBaseAdapter<FollowAndFan> {
    public FollowAndFanAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, FollowAndFan item) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new FollowAndFanHolder(super.mContext);
    }
}
