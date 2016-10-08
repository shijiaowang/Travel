package com.example.administrator.travel.ui.appoint.withme.withmedetail;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 找人带目的地详情
 */
public class AppointWithMeDetailDestinationAdapter extends TravelBaseAdapter<AppointWithMeDetailBean.DataBean.RoutesBean> {


    public AppointWithMeDetailDestinationAdapter(Context mContext, List<AppointWithMeDetailBean.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointWithMeDetailBean.DataBean.RoutesBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointWithMeDetailDestinationHolder(mContext);
    }
}
