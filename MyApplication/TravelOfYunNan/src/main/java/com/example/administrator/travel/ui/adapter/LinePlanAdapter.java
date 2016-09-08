package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.activity.SelectDestinationActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.LinePlanHolder;

import java.util.List;
import java.util.Stack;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class LinePlanAdapter extends TravelBaseAdapter<Line> {
    public LinePlanAdapter(Context mContext, List mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 10;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final Line item, int position) {
        if (baseHolder instanceof LinePlanHolder){
            final LinePlanHolder linePlanHolder = (LinePlanHolder) baseHolder;
            linePlanHolder.mTvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, SelectDestinationActivity.class));
                }
            });
        }
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new LinePlanHolder(mContext);
    }
}
