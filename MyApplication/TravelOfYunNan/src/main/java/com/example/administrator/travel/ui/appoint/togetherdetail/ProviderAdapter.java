package com.example.administrator.travel.ui.appoint.togetherdetail;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/5 0005.
 * 约伴详情道具提供
 */
public class ProviderAdapter extends TravelBaseAdapter {
    public ProviderAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ProviderHolder(mContext);
    }
}
