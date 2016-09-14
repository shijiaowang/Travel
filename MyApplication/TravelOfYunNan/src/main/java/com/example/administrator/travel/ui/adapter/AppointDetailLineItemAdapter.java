package com.example.administrator.travel.ui.adapter;


import android.content.Context;

import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.holer.AppointDetailLineItemHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineItemAdapter extends TravelBaseAdapter<AppointTogetherDetail.DataBean.RoutesBean>{

    public AppointDetailLineItemAdapter(Context mContext, List<AppointTogetherDetail.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointTogetherDetail.DataBean.RoutesBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailLineItemHolder(mContext);
    }
}