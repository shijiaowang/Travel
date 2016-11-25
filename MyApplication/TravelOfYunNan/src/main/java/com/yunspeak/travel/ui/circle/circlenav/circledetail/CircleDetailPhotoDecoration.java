package com.yunspeak.travel.ui.circle.circlenav.circledetail;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/11/25 0025.
 */

public class CircleDetailPhotoDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public CircleDetailPhotoDecoration(int space) {
        this.space = DensityUtil.dip2px(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view)!=0 && parent.getChildAdapterPosition(view)!=3)
            outRect.left = space;
        outRect.top=space;
    }
}
