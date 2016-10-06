package com.example.administrator.travel.ui.appoint.withme;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class AppointWithMeAdapter extends TravelBaseAdapter<AppointWithMeBean.DataBean> {
    public AppointWithMeAdapter(Context mContext, List<AppointWithMeBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, AppointWithMeBean.DataBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointWithMeHolder(mContext);
    }
}
