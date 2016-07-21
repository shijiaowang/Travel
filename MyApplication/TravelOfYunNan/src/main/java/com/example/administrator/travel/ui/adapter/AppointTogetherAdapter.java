package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.AppointTogether;
import com.example.administrator.travel.ui.adapter.holer.AppointTogetherHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointTogetherAdapter extends TravelBaseAdapter<AppointTogether> {
    public AppointTogetherAdapter(Context mContext, List<AppointTogether> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointTogether item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointTogetherHolder(mContext);
    }
}
