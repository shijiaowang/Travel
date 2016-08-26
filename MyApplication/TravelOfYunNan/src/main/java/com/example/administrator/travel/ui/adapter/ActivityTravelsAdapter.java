package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Travels;
import com.example.administrator.travel.ui.adapter.holer.ActivityTravelsHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class ActivityTravelsAdapter extends TravelBaseAdapter<Travels.DataBean> {
    public ActivityTravelsAdapter(Context mContext, List<Travels.DataBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, Travels.DataBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ActivityTravelsHolder(mContext);
    }
}
