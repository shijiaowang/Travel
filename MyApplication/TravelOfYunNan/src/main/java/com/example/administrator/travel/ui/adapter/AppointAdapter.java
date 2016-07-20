package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Appoint;
import com.example.administrator.travel.ui.adapter.holer.AppointHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class AppointAdapter extends TravelBaseAdapter<Appoint> {
    public AppointAdapter(Context mContext, List<Appoint> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Appoint item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointHolder(mContext);
    }
}
