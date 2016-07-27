package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleNavLeftHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleNavLeftAdapter extends TravelBaseAdapter<Circle.DataBean.CircleLeftBean> {

    public CircleNavLeftAdapter(Context mContext, List<Circle.DataBean.CircleLeftBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 20;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Circle.DataBean.CircleLeftBean item) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleNavLeftHolder(super.mContext);
    }
}
