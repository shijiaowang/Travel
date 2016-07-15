package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.SystemMessage;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.SystemMessageHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessageAdapter extends TravelBaseAdapter<SystemMessage> {
    public SystemMessageAdapter(Context mContext, List<SystemMessage> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, SystemMessage item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new SystemMessageHolder(super.mContext);
    }
}
