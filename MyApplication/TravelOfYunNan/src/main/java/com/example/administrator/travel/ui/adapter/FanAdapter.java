package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Fan;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.FanHolder;


import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class FanAdapter extends TravelBaseAdapter<Fan.FanPeople> {
    public FanAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Fan.FanPeople item, int position) {

    }





    @Override
    protected BaseHolder initHolder(int position) {
        return new FanHolder(super.mContext);
    }
}
