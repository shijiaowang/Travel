package com.yunspeak.travel.ui.find.travels;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 */
public class TravelsAdapter extends TravelBaseAdapter<TravelsBean.DataBean> {
    public TravelsAdapter(Context mContext, List<TravelsBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, TravelsBean.DataBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TravelsHolder(mContext);
    }
}
