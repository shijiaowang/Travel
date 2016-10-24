package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class FastQueryIndex extends LinearLayout {
    private List<String> indexList=new ArrayList<>();
    int indexColor = Color.parseColor("#a7a7a7");

    public FastQueryIndex(Context context) {
        this(context, null);
    }

    public FastQueryIndex(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastQueryIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 初始化索引
     * @param indexWord
     */
    public void initWordIndex(List<String> indexWord){
        this.indexList=indexWord;
        drawIndexWord();

    }


    /**
     * 绘制字典索引
     */
    private void drawIndexWord() {
        if (getChildCount()>0){
            removeAllViews();
        }
        for (int i = 0; i < indexList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(indexColor);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            layoutParams.weight = 1;
            textView.setText(indexList.get(i));
            textView.setLayoutParams(layoutParams);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        char index = indexList.get(finalI).charAt(0);
                        onItemClickListener.onClickWord(index);//传回索引
                    }
                }
            });
            addView(textView);
        }

    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClickWord(char c);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
