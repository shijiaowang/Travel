package com.yunspeak.travel.ui.home.homesearch;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/22 0022.
 */
public class SearchCommonAdapter extends BaseRecycleViewAdapter<SearchCommonBean.DataBean> {


    private final String type;

    public SearchCommonAdapter(List<SearchCommonBean.DataBean> mDatas, Context mContext, String type) {
        super(mDatas, mContext);
        this.type = type;
    }


    @Override
    public BaseRecycleViewHolder<SearchCommonBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchCommonHolder(inflateView(R.layout.item_activity_home_search_1,parent),type);
    }
}
