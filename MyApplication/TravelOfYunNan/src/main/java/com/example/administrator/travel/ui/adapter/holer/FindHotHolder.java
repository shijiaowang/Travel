package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.FindHot;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindHotHolder extends BaseHolder<FindHot>{


    public FindHotHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(FindHot datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_find_hot);
        return inflate;
    }
}
