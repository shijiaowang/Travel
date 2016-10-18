package com.example.administrator.travel.ui.appoint.customdestination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

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
