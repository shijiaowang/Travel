package com.yunspeak.travel.ui.me.messagecenter.systemmessage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class SystemMessageAdapter extends BaseRecycleViewAdapter<SystemMessageBean.DataBean> {


    public SystemMessageAdapter(List<SystemMessageBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<SystemMessageBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SystemMessageHolder(inflateView(R.layout.item_activity_system_message,parent));
    }
}
