package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/9/5 0005.
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
