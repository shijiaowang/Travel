package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.PopEquHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class PopEquAdapter extends TravelBaseAdapter{
    public PopEquAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new PopEquHolder(mContext);
    }
}
