package com.yunspeak.travel.ui.me.mytheme;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/3 0003.
 */
public class ThemeCommonAdapter extends TravelBaseAdapter {

    public ThemeCommonAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ThemeCommonHolder(mContext);
    }
}
