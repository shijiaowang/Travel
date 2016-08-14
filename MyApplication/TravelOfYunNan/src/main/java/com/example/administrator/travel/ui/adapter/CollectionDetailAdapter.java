package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by android on 2016/8/14.
 */
public class CollectionDetailAdapter extends TravelBaseAdapter {
    public CollectionDetailAdapter(Context mContext, List mDatas) {
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
        return null;
    }
}
