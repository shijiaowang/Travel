package com.example.administrator.travel.ui.appoint.selectdestination;

import android.content.Context;

import com.example.administrator.travel.bean.Destination;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class SelectDestinationAdapter extends TravelBaseAdapter<Destination.DataBean.BodyBean>{
    public SelectDestinationAdapter(Context mContext, List<Destination.DataBean.BodyBean> mDatas) {
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
        return new SelectDestinationHolder(mContext);
    }
}
