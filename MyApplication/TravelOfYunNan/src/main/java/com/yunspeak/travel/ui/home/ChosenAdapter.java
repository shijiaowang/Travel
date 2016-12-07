package com.yunspeak.travel.ui.home;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 主页精选
 */
public class ChosenAdapter extends BaseRecycleViewAdapter<HomeBean.DataBean.ForumBean> {


    public ChosenAdapter(List<HomeBean.DataBean.ForumBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<HomeBean.DataBean.ForumBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChosenHolder(inflateView(R.layout.item_fragment_home_chosen, parent));
    }
}
