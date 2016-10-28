package com.yunspeak.travel.ui.appoint.together.togetherdetail;


import android.content.Context;
import android.view.View;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.baseui.DestinationDetailActivity;

import java.util.List;

/**
 * Created by wangyang on 2016/9/5 0005.
 */
public class AppointDetailLineItemAdapter extends TravelBaseAdapter<AppointTogetherDetailBean.DataBean.RoutesBean> {

    public AppointDetailLineItemAdapter(Context mContext, List<AppointTogetherDetailBean.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final AppointTogetherDetailBean.DataBean.RoutesBean item, int position) {
        AppointDetailLineItemHolder appointDetailLineItemHolder = (AppointDetailLineItemHolder) baseHolder;
        appointDetailLineItemHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationDetailActivity.start(mContext,item.getId(),item.getTitle());
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailLineItemHolder(mContext);
    }
}
