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
 */
public class FindRecommendAdapter extends BaseRecycleViewAdapter<FindBean.DataBean.RecommendBean> {


    public FindRecommendAdapter(List<FindBean.DataBean.RecommendBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }



    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FindRecommendHolder(inflateView(R.layout.item_fragment_find_recommend, parent));
    }

}
