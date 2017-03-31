package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/3/15.
 * 我的 页面的 item
 */

public class ItemView extends RelativeLayout {

    private int drawableLeft;
    private String text;

    public ItemView(Context context) {
        this(context,null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();//获取自定义属性个数
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.ItemView_item_drawable:
                    drawableLeft = typedArray.getResourceId(index,0);
                    break;
                case R.styleable.ItemView_item_text:
                    text = typedArray.getString(index);
                    break;
            }
        }
        typedArray.recycle();
        Resources resources = getContext().getResources();
        TextView textView=new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#646464"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        textView.setCompoundDrawablePadding((int) resources.getDimension(R.dimen.x10));
        if (drawableLeft!=0) {
            Drawable drawable = resources.getDrawable(drawableLeft);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin= (int) resources.getDimension(R.dimen.x20);
        layoutParams.addRule(CENTER_VERTICAL);
        textView.setLayoutParams(layoutParams);
        ImageView imageView=new ImageView(getContext());
        imageView.setImageResource(R.drawable.fragment_circle_nav_left_normal);
        RelativeLayout.LayoutParams imageLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.rightMargin= (int) resources.getDimension(R.dimen.x22);
        imageLayoutParams.addRule(CENTER_VERTICAL);
        imageLayoutParams.addRule(ALIGN_PARENT_RIGHT);
        imageView.setLayoutParams(imageLayoutParams);
        addView(textView);
        addView(imageView);

    }
}
