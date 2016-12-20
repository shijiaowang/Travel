package com.yunspeak.travel.ui.find;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/19 0019.
 * 发现页面人们推荐
 */
public class FindHotAdapter extends BaseRecycleViewAdapter<FindBean.DataBean.RecommendBean> {


    public FindHotAdapter(List<FindBean.DataBean.RecommendBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FindHotHolder(inflateView(R.layout.item_fragment_find_hot, parent));
    }


}
