package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class MyCollectionDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int top;

    public MyCollectionDecoration(int space,int top) {
        this.space = space;
        this.top = top;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view)!=0) {
            outRect.top = DensityUtil.dip2px(space);
        }else {
            outRect.top=DensityUtil.dip2px(top);
        }
    }
}