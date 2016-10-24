package com.yunspeak.travel.ui.adapter;

import android.content.Context;

import com.yunspeak.travel.bean.Dynamic;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.DynamicHoler;

import java.util.List;

/**
 * Created by wangyang on 2016/7/14 0014.
 */
public class DynamicAdapter extends TravelBaseAdapter<Dynamic> {
    public DynamicAdapter(Context mContext, List<Dynamic> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 20;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Dynamic item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new DynamicHoler(super.mContext);
    }
}
