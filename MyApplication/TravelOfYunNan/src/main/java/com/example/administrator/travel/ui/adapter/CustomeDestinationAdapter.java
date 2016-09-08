package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CustomeDestinationHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class CustomeDestinationAdapter extends TravelBaseAdapter {
    public CustomeDestinationAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CustomeDestinationHolder(mContext);
    }
}
