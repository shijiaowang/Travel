package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.FindRecommend;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindRecommendHolder extends BaseHolder<FindRecommend> {
    public FindRecommendHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(FindRecommend datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_find_recommend);
        return inflate;
    }
}
