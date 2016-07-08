package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleNavLeftHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftAdapter extends TravelBaseAdapter<CircleNavLeft> {
    public CircleNavLeftAdapter(Context mContext, List<CircleNavLeft> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 20;
    }

    @Override
    protected BaseHolder initHolder() {
        return new CircleNavLeftHolder(super.mContext);
    }
}
