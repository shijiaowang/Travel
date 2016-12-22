package com.yunspeak.travel.ui.appoint.together.togetherdetail;


import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointTogetherDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class AppointDetailLineItemAdapter extends BaseRecycleViewAdapter<AppointTogetherDetailBean.DataBean.RoutesBean> {


    public AppointDetailLineItemAdapter(List<AppointTogetherDetailBean.DataBean.RoutesBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }



    @Override
    public BaseRecycleViewHolder<AppointTogetherDetailBean.DataBean.RoutesBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointDetailLineItemHolder(inflateView(R.layout.item_appoint_detail_line_item,parent));
    }
}
