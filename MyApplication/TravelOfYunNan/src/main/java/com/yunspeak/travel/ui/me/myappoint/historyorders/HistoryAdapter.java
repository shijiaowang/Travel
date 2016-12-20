package com.yunspeak.travel.ui.me.myappoint.historyorders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.bean.MyAppointTogetherBean;
import com.yunspeak.travel.ui.me.myappoint.MyAppointTogetherHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class HistoryAdapter extends BaseRecycleViewAdapter<MyAppointTogetherBean.DataBean> {
    public static final  int TYPE_TOGETHER=1;
    public static final  int TYPE_WITH_ME=2;
    public HistoryAdapter(List<MyAppointTogetherBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<MyAppointTogetherBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflateView(R.layout.item_activity_my_appoint_together, parent);
            return new MyAppointTogetherHolder(view);


    }



}
