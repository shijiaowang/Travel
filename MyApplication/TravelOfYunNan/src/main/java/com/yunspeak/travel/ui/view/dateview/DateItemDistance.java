package com.yunspeak.travel.ui.view.dateview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/2/24.
 */

public class DateItemDistance extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom= UIUtils.getDimen(R.dimen.x15);
    }
}
