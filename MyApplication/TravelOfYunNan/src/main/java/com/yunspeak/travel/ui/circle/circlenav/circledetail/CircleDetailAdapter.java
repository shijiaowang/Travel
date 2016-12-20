package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ActivityBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.find.active.ActiveHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/11 0011.
 */
public class CircleDetailAdapter extends BaseRecycleViewAdapter<Object> {
    private static final int POST=1;
    private static final int ACTIVITY=2;

    public CircleDetailAdapter(List<Object> list, Context mContext) {
        super(list, mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position) instanceof ActivityBean){
            return ACTIVITY;
        }
        return POST;
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ACTIVITY){
             return new ActiveHolder(inflateView(R.layout.item_activity_active,parent));
        }else {
            return new CircleDetailHolder(inflateView(R.layout.item_activity_circle, parent));
        }
    }


}
