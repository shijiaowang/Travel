package com.example.administrator.travel.ui.appoint.aite;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class AiteAdapter extends TravelBaseAdapter<AiteFollow> {
    public AiteAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 22;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, AiteFollow item, int position) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new AiteHolder(mContext);
    }
}
