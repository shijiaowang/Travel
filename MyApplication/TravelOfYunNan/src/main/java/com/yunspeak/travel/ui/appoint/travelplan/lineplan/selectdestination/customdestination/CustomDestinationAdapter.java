package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class CustomDestinationAdapter extends BaseRecycleViewAdapter<CustomDestinationBean.DataBean> {

    public CustomDestinationAdapter(List<CustomDestinationBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<CustomDestinationBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.item_activity_custom_destination, parent);
        return new CustomDestinationHolder(view);
    }
}
