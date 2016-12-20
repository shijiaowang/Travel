package com.yunspeak.travel.ui.appoint.withme;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AppointWithMeBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/21 0021.
 */
public class AppointWithMeAdapter extends BaseRecycleViewAdapter<AppointWithMeBean.DataBean> {

    public AppointWithMeAdapter(List<AppointWithMeBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<AppointWithMeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointWithMeHolder(inflateView(R.layout.item_fragment_appoint_play_with_me,parent));
    }
}
