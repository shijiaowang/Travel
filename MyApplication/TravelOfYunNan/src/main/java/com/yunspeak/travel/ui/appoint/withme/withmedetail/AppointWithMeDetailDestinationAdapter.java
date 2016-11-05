package com.yunspeak.travel.ui.appoint.withme.withmedetail;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 找人带目的地详情
 */
public class AppointWithMeDetailDestinationAdapter extends TravelBaseAdapter<AppointWithMeDetailBean.DataBean.RoutesBean> {


    public AppointWithMeDetailDestinationAdapter(Context mContext, List<AppointWithMeDetailBean.DataBean.RoutesBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final AppointWithMeDetailBean.DataBean.RoutesBean item, int position) {
       baseHolder.root.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DestinationDetailActivity.start(mContext,item.getId(),item.getTitle());
           }
       });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointWithMeDetailDestinationHolder(mContext);
    }
}
