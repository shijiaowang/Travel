package com.yunspeak.travel.ui.appoint.customdestination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadingBarBaseActivity;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class CustomDestinationAdapter extends BaseRecycleViewAdapter<CustomDestinationBean.DataBean> {

    public CustomDestinationAdapter(List<CustomDestinationBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<CustomDestinationBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateView(R.layout.item_activity_custom_destination, parent);
        return new CustomDestinationHolder(view);
    }
}
