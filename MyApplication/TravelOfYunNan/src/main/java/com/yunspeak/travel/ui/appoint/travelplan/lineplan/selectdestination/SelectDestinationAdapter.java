package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.bean.FindDestinationBean;

import java.util.List;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class SelectDestinationAdapter extends BaseRecycleViewAdapter<FindDestinationBean.DataBean.BodyBean> {


    public SelectDestinationAdapter(List<FindDestinationBean.DataBean.BodyBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<FindDestinationBean.DataBean.BodyBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectDestinationHolder(inflateView(R.layout.item_activity_select_destination,parent));
    }
}
