package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CircleAdapter extends TravelBaseAdapter<Circle> {
    public CircleAdapter(Context mContext, List<Circle> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 3;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Circle item) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleHolder(super.mContext);
    }
}
