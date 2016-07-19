package com.example.administrator.travel.ui.adapter;

import android.content.Context;

import com.example.administrator.travel.bean.FindHot;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.FindHotHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19 0019.
 * 发现页面人们推荐
 */
public class FindHotAdapter  extends TravelBaseAdapter<FindHot>{
    public FindHotAdapter(Context mContext, List<FindHot> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, FindHot item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new FindHotHolder(mContext);
    }
}
