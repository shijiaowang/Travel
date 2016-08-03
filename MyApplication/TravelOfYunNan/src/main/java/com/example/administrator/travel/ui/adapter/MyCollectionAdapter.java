package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.MyCollectionHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MyCollectionAdapter extends TravelBaseAdapter {
    public MyCollectionAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 8;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new MyCollectionHolder(mContext);
    }
}
