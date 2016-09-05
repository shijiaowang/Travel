package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleHot;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotHolder extends BaseHolder<CircleHot> {
    public CircleHotHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleHot circleHot, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        View root = inflateView(R.layout.item_fragment_circle_hot);
        return root;
    }
}
