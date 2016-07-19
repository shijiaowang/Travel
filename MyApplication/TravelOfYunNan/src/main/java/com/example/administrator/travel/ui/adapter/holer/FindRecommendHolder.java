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
    protected void initItemDatas(FindRecommend datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_find_recommend, null);
        return inflate;
    }
}
