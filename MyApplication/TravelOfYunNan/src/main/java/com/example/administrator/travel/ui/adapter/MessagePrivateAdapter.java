package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.SystemPrivate;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.MessagePrivateHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class MessagePrivateAdapter extends TravelBaseAdapter<SystemPrivate> {
    public MessagePrivateAdapter(Context mContext, List<SystemPrivate> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, SystemPrivate item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new MessagePrivateHolder(super.mContext);
    }
}
