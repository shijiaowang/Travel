package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.bean.Line;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.LinePlanHolder;

import java.util.List;

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
    protected void initListener(BaseHolder baseHolder,  final Line item) {
        if (baseHolder instanceof LinePlanHolder){
            final LinePlanHolder linePlanHolder = (LinePlanHolder) baseHolder;
            linePlanHolder.mTvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> add = item.getAdd();
                    add.add("这是新加的");
                    item.setAdd(add);
                    notifyData(mDatas);
                }
            });
        }
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new LinePlanHolder(mContext);
    }
}
