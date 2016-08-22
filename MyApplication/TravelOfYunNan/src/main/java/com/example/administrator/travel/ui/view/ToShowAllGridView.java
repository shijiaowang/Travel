package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 解决嵌套ScorllView只显示一行数据
 */
public class ToShowAllGridView extends GridView {

    private boolean isClick=true;

    public ToShowAllGridView(Context context) {
        super(context);
    }

    public ToShowAllGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToShowAllGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新测量高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
   public void setCanClick(boolean isClick){
       this.isClick = isClick;
   }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isClick;
    }
}
