package com.yunspeak.travel.ui.circle.circlenav;

import android.content.Context;

import com.yunspeak.travel.bean.Circle;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class CircleNavLeftAdapter extends TravelBaseAdapter<Circle.DataBean.CircleLeftBean> {

    public CircleNavLeftAdapter(Context mContext, List<Circle.DataBean.CircleLeftBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, Circle.DataBean.CircleLeftBean item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleNavLeftHolder(super.mContext);
    }
}
