package com.yunspeak.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class SearchUserHolder extends BaseHolder {
    public SearchUserHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View view = inflateView(R.layout.item_activity_home_search_1);
        return view;
    }
}
