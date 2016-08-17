package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.Discuss;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.DeliciousDiscussHoler;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDiscussAdapter extends TravelBaseAdapter<Discuss> {
    public DeliciousDiscussAdapter(Context mContext, List<Discuss> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 5;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Discuss item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {

        return new DeliciousDiscussHoler(mContext);
    }
}
