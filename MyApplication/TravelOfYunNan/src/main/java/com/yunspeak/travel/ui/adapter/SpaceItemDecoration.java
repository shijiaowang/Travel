package com.yunspeak.travel.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom=space;
        if (parent.getChildAdapterPosition(view) % 2 == 1)
            outRect.left = space;
    }
}