package com.yunspeak.travel.ui.circle.circlenav;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class CircleNavRightAdapter extends TravelBaseAdapter<CircleNavRight.RightCircle> {
    public CircleNavRightAdapter(Context mContext, List<CircleNavRight.RightCircle> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, CircleNavRight.RightCircle item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleNavRightHolder(super.mContext);
    }
}
