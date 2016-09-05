package com.example.administrator.travel.ui.adapter;


import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.AppointDetailLineItemHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineItemAdapter extends TravelBaseAdapter<String>{

    public AppointDetailLineItemAdapter(Context mContext, List<String> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, String item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailLineItemHolder(mContext);
    }
}
