package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.holer.AppointDetailInsuranceHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailInsuranceAdapter extends TravelBaseAdapter<AppointTogetherDetail.DataBean.PricebasecBean> {
    public AppointDetailInsuranceAdapter(Context mContext, List<AppointTogetherDetail.DataBean.PricebasecBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointTogetherDetail.DataBean.PricebasecBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailInsuranceHolder(mContext);
    }
}
