package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.BulletinBoardHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class BulletinBoardAdapter extends TravelBaseAdapter{
    public BulletinBoardAdapter(Context mContext, List mDatas) {
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
        return new BulletinBoardHolder(mContext);
    }
}
