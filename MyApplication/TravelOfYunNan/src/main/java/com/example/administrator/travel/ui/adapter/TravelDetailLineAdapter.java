package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.appoint.togetherdetail.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.TravelDetailLineHolder;

import java.util.List;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineAdapter extends TravelBaseAdapter<List<AppointTogetherDetail.DataBean.RoutesBean>> {

    private boolean isDetail;

    public TravelDetailLineAdapter(Context mContext, List<List<AppointTogetherDetail.DataBean.RoutesBean>> mDatas, boolean isDetail) {
        super(mContext, mDatas);
        this.isDetail = isDetail;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, List<AppointTogetherDetail.DataBean.RoutesBean> item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TravelDetailLineHolder(mContext,isDetail);
    }
}
