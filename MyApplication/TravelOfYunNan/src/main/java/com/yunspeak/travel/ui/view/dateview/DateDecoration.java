package com.yunspeak.travel.ui.view.dateview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.util.DensityUtil;
import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/2/21.
 */
public class DateDecoration extends RecyclerView.ItemDecoration {
    private String[] monthTitles;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private DecorationCallback callback;
    private final int lineHeight;
    private final Paint linPaint;


    interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }


    public DateDecoration(String[] monthTitles, Context context, DecorationCallback callback) {
        this.monthTitles = monthTitles;
        this.callback = callback;
        linPaint = new Paint();
        linPaint.setColor(Color.parseColor("#d9d9d9"));
        //设置悬浮栏的画笔
        paint = new Paint();
        paint.setColor(UIUtils.getColor(R.color.colorefeff4));
        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.sp2px(context,14));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(UIUtils.getColor(R.color.color646464));
        topGap = UIUtils.getDimen(R.dimen.x48);
        lineHeight = UIUtils.getDimen(R.dimen.x1);


    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = topGap;
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        String preGroupId;
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId.equals("-1") || groupId.equals(preGroupId)) continue;
            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;
            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupId = callback.getGroupId(position + 1);
                //组内最后一个view进入了header
                if (!nextGroupId.equals(groupId) && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY-toGap决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - topGap, right, textY, paint);
            //绘制一条线
            c.drawRect(left,textY,right,textY+lineHeight,linPaint);
            Rect rect = new Rect();
            String monthTitle = monthTitles[position];
            textPaint.getTextBounds(monthTitle, 0, monthTitle.length(), rect);
            c.drawText(monthTitle,left+right/2,textY-(topGap-rect.height())/2,textPaint);

        }
    }


}

