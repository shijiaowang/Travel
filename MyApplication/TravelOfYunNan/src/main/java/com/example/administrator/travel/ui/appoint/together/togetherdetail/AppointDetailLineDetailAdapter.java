package com.example.administrator.travel.ui.appoint.together.togetherdetail;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineDetailAdapter extends TravelBaseAdapter<AppointTogetherDetail.DataBean.RoutesBean> {
    public AppointDetailLineDetailAdapter(Context mContext, List<AppointTogetherDetail.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AppointTogetherDetail.DataBean.RoutesBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailLineDetailHolder(mContext);
    }
}
