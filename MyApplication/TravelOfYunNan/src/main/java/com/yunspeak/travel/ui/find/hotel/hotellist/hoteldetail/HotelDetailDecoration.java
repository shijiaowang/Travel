package com.yunspeak.travel.ui.find.hotel.hotellist.hoteldetail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;


/**
 * Created by wangyang on 2017/2/28.
 * 增加Recycleview的线条
 */

public class HotelDetailDecoration extends RecyclerView.ItemDecoration {
    private int left;
    private final Paint paint;
    private final int lineHeight;

    private HotelDetailDecoration(int left){
        this.left = left;
        lineHeight = UIUtils.getDimen(R.dimen.x1);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(UIUtils.getColor(R.color.meLine));
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int width = parent.getWidth();
        int childCount = parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View childAt = parent.getChildAt(i);
            int bottom = childAt.getBottom();
            c.drawLine(left,bottom-lineHeight,width-left,bottom,paint);
        }
    }
}
