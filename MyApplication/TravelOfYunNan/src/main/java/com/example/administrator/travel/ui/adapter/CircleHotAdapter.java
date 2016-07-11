package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.CircleHot;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleHotHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotAdapter extends TravelBaseAdapter<CircleHot> {


    public CircleHotAdapter(Context mContext, List<CircleHot> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 2;
    }



    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleHotHolder(super.mContext);
    }


}
