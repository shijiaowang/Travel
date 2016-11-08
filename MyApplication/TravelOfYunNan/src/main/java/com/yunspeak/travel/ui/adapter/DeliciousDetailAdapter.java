package com.yunspeak.travel.ui.adapter;

import android.content.Context;

import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.DeliciousDetailHoler;

import java.util.List;

/**
 * Created by wangyang on 2016/7/26 0026.
 */
public class DeliciousDetailAdapter extends TravelBaseAdapter<DeliciousDetailBean> {
    public DeliciousDetailAdapter(Context mContext, List<DeliciousDetailBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 3;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, DeliciousDetailBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new DeliciousDetailHoler(mContext);
    }
}
