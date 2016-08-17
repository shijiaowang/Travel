package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.FindRecommend;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.FindRecommendHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindRecommendAdapter extends TravelBaseAdapter<FindRecommend> {
    public FindRecommendAdapter(Context mContext, List<FindRecommend> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 4;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, FindRecommend item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new FindRecommendHolder(mContext);
    }
}
