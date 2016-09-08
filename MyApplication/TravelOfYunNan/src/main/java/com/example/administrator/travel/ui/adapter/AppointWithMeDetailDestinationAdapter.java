package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.AppointWithMeDetail;
import com.example.administrator.travel.ui.adapter.holer.AppointWithMeDetailDestinationHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 找人带目的地详情
 */
public class AppointWithMeDetailDestinationAdapter extends TravelBaseAdapter<AppointWithMeDetail.DataBean.RoutesBean> {


    public AppointWithMeDetailDestinationAdapter(Context mContext, List<AppointWithMeDetail.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointWithMeDetail.DataBean.RoutesBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointWithMeDetailDestinationHolder(mContext);
    }
}
