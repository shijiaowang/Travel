package com.yunspeak.travel.ui.appoint.together.togetherdetail;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class AppointDetailLineDetailAdapter extends TravelBaseAdapter<AppointTogetherDetailBean.DataBean.RoutesBean> {
    public AppointDetailLineDetailAdapter(Context mContext, List<AppointTogetherDetailBean.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final AppointTogetherDetailBean.DataBean.RoutesBean item, int position) {
        AppointDetailLineDetailHolder appointDetailLineItemHolder = (AppointDetailLineDetailHolder) baseHolder;
        appointDetailLineItemHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinationDetailActivity.start(mContext,item.getId(),item.getTitle());
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointDetailLineDetailHolder(mContext);
    }
}
