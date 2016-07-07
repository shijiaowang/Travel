package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Created by Administrator on 2016/7/6 0006.
 * 解决嵌套ScrollView只显示一行问题
 */
public class ToShowAllListView extends ListView {
    public ToShowAllListView(Context context) {
        super(context);
    }

    public ToShowAllListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToShowAllListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
