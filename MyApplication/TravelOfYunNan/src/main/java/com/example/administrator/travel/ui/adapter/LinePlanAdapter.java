package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.LinePlanHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanAdapter extends TravelBaseAdapter {
    public LinePlanAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new LinePlanHolder(mContext);
    }
}
