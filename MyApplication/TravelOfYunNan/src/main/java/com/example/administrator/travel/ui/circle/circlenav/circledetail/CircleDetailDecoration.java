package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class CircleDetailDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CircleDetailDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = DensityUtil.dip2px(space);
    }
}