package com.yunspeak.travel.ui.circle.hotpost;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class CircleHotAdapter extends BaseRecycleViewAdapter<HotPostBean.DataBean> {


    public CircleHotAdapter(List<HotPostBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HotPostBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CircleHotHolder(inflateView(R.layout.item_fragment_circle_hot,parent));
    }
}
