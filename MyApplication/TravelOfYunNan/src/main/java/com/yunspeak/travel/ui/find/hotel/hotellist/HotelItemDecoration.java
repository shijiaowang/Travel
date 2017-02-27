package com.yunspeak.travel.ui.find.hotel.hotellist;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/2/27.
 */

public class HotelItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)==0){
            outRect.top= UIUtils.getDimen(R.dimen.x13);
        }
    }
}
