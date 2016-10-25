package com.yunspeak.travel.ui.appoint.selectdestination;

import android.content.Context;

import com.yunspeak.travel.bean.Destination;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class SelectDestinationAdapter extends TravelBaseAdapter<Destination.DataBean.BodyBean>{
    public SelectDestinationAdapter(Context mContext, List<Destination.DataBean.BodyBean> mDatas) {
        super(mContext, mDatas);

    }

    @Override
    protected void initListener(BaseHolder baseHolder, final Destination.DataBean.BodyBean item, final int position) {
    }



    @Override
    protected BaseHolder initHolder(int position) {
        return new SelectDestinationHolder(mContext);
    }
}