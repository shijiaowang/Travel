package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.ChoicePropsRightHodler;

import java.util.List;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ChoicePropsRightAdapter extends TravelBaseAdapter {
    public ChoicePropsRightAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Object item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ChoicePropsRightHodler(mContext);
    }
}
