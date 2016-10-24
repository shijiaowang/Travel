package com.yunspeak.travel.ui.adapter;

import android.content.Context;

import com.yunspeak.travel.bean.Destination;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.DestinationHoler;

import java.util.List;

/**
 * Created by android on 2016/7/30.
 */
public class DestinationAdapter extends TravelBaseAdapter<Destination.DataBean.BodyBean> {
    public DestinationAdapter(Context mContext, List<Destination.DataBean.BodyBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Destination.DataBean.BodyBean item, int position) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new DestinationHoler(mContext);
    }
}
