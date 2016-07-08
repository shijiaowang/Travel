package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleNavRightHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavRightAdapter extends TravelBaseAdapter<CircleNavRight> {
    public CircleNavRightAdapter(Context mContext, List<CircleNavRight> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected BaseHolder initHolder() {
        return new CircleNavRightHolder(super.mContext);
    }
}
