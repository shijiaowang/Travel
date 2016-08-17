package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.DeliciousDetail;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.DeliciousDetailHoler;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class DeliciousDetailAdapter extends TravelBaseAdapter<DeliciousDetail> {
    public DeliciousDetailAdapter(Context mContext, List<DeliciousDetail> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 3;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, DeliciousDetail item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new DeliciousDetailHoler(mContext);
    }
}
