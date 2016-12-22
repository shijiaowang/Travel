package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/9/4.
 */
public class TravelDetailLineAdapter extends BaseRecycleViewAdapter<List<AppointTogetherDetailBean.DataBean.RoutesBean>> {

    public boolean isDetail=true;

    public TravelDetailLineAdapter(List<List<AppointTogetherDetailBean.DataBean.RoutesBean>> mDatas, Context mContext,boolean isDetail) {
        super(mDatas, mContext);
        this.isDetail = isDetail;
    }


    @Override
    public BaseRecycleViewHolder<List<AppointTogetherDetailBean.DataBean.RoutesBean>> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TravelDetailLineHolder(inflateView(R.layout.item_activity_appoint_detail_line,null),isDetail);
    }
}
