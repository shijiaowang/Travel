package com.yunspeak.travel.ui.me.fans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/3/21.
 * 右上角一条细线
 */

public class LineDecoration extends RecyclerView.ItemDecoration {
    private int leftDistance;//线条距离左边的距离
    private int rightDistance;
    private  Paint paint;

    public LineDecoration() {
        this.leftDistance =UIUtils.getDimen(R.dimen.x71);
        this.rightDistance= UIUtils.getDimen(R.dimen.x13);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(UIUtils.getColor(R.color.meLine));
        paint.setAntiAlias(true);

    }
    public LineDecoration(int left,int right) {
        this.leftDistance =left;
        this.rightDistance= right;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(UIUtils.getColor(R.color.meLine));
        paint.setAntiAlias(true);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(childAt);
            if (childAdapterPosition==0)continue;//第一个右上角不需要绘制横线
            int top = childAt.getTop();
            int right = childAt.getRight();
            c.drawRect(leftDistance,top,right-rightDistance,top+UIUtils.getDimen(R.dimen.x1),paint);
        }
    }
}
