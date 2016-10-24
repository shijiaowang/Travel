package com.yunspeak.travel.ui.me.othercenter.useralbum;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class AlbumSpace extends RecyclerView.ItemDecoration {

    private int space;

    public AlbumSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view)>1) {
            outRect.top = DensityUtil.dip2px(space);
        }
        if (parent.getChildAdapterPosition(view)%2==1){
            outRect.left=DensityUtil.dip2px(space);
        }
    }
}