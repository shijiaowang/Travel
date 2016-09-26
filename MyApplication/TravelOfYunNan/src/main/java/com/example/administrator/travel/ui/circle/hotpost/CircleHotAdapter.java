package com.example.administrator.travel.ui.circle.hotpost;

import android.content.Context;

import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotAdapter extends TravelBaseAdapter<HotPostBean.DataBean> {


    public CircleHotAdapter(Context mContext, List<HotPostBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, HotPostBean.DataBean item, int position) {

    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleHotHolder(super.mContext);
    }


}
