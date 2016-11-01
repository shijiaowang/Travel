package com.yunspeak.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageAdapter extends BaseRecycleViewAdapter<CommonMessageBean.DataBean> {


    public AppointMessageAdapter(List<CommonMessageBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<CommonMessageBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointMessageHolder(inflateView(R.layout.item_activity_appoint_message,parent));
    }
}
