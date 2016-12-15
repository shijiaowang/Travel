package com.yunspeak.travel.ui.find;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.common.util.DensityUtil;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 修改recycleview孩子之间的间距
 */
public class FindDecoration extends RecyclerView.ItemDecoration {


    private int top;
    private  int left;

    public FindDecoration(int top, int left) {
        this.top = top;


        this.left = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (childAdapterPosition%2==1) {
            outRect.left = DensityUtil.dip2px(left);
        }
        if (childAdapterPosition>=2){
            outRect.top = DensityUtil.dip2px(top);
        }
    }
}