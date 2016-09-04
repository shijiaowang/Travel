package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.TravelDetailLineHolder;

import java.util.List;

/**
 * Created by android on 2016/9/4.
 */
public class TravelDetailLineAdapter extends TravelBaseAdapter {
    public TravelDetailLineAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TravelDetailLineHolder(mContext);
    }
}
