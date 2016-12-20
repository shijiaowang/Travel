package com.yunspeak.travel.ui.appoint.searchappoint;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.bean.AppointTogetherBean;
import com.yunspeak.travel.ui.appoint.together.AppointTogetherHolder;
import com.yunspeak.travel.ui.appoint.withme.AppointWithMeHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/11/11 0011.
 */

public class SearchAppointAdapter extends BaseRecycleViewAdapter<Object> {
    public SearchAppointAdapter(List<Object> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mDatas.get(position);
        if (o instanceof AppointTogetherBean.DataBean) {
            return IVariable.TYPE_TOGETHER;
        } else
            return IVariable.TYPE_WITH_ME;
    }

    @Override
    public BaseRecycleViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType==IVariable.TYPE_TOGETHER){
                return new AppointTogetherHolder(inflateView(R.layout.item_fragment_appoint_play_together,parent));
           }
           return new AppointWithMeHolder(inflateView(R.layout.item_fragment_appoint_play_with_me,parent));
    }
}
