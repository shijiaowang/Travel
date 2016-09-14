package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.appoint.togetherdetail.AppointTogetherDetail;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.ProviderHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 * 约伴详情道具提供
 */
public class ProviderAdapter extends TravelBaseAdapter<AppointTogetherDetail.DataBean.PropBean> {
    public ProviderAdapter(Context mContext, List<AppointTogetherDetail.DataBean.PropBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointTogetherDetail.DataBean.PropBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ProviderHolder(mContext);
    }
}
