package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.AppointWithMe;
import com.example.administrator.travel.ui.adapter.holer.AppointWithMeHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMeAdapter extends TravelBaseAdapter<AppointWithMe> {
    public AppointWithMeAdapter(Context mContext, List<AppointWithMe> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointWithMe item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointWithMeHolder(mContext);
    }
}
