package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.bean.TravelsDetail;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.TravelDetailLineHolder;

import java.util.List;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineAdapter extends TravelBaseAdapter<List<AppointTogetherDetail.DataBean.RoutesBean>> {

    public TravelDetailLineAdapter(Context mContext, List<List<AppointTogetherDetail.DataBean.RoutesBean>> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, List<AppointTogetherDetail.DataBean.RoutesBean> item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TravelDetailLineHolder(mContext);
    }
}
