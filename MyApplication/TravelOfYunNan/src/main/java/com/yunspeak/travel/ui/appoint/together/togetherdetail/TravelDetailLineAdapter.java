package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/9/4.
 */
public class TravelDetailLineAdapter extends TravelBaseAdapter<List<AppointTogetherDetailBean.DataBean.RoutesBean>> {

    public boolean isDetail=true;

    public TravelDetailLineAdapter(Context mContext, List<List<AppointTogetherDetailBean.DataBean.RoutesBean>> mDatas,boolean isDetail) {
        super(mContext, mDatas);
        this.isDetail = isDetail;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, List<AppointTogetherDetailBean.DataBean.RoutesBean> item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TravelDetailLineHolder(mContext,isDetail);
    }
}
