package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.MemberJoinedHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MemberJoinedAdapter extends TravelBaseAdapter {
    public MemberJoinedAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 6;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new MemberJoinedHolder(mContext);
    }
}
