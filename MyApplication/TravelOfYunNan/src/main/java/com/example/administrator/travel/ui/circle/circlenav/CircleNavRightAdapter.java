package com.example.administrator.travel.ui.circle.circlenav;

import android.content.Context;

import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
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
