package com.example.administrator.travel.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class HotSpotsItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public HotSpotsItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view)!=0)
            outRect.left = space;
    }
}