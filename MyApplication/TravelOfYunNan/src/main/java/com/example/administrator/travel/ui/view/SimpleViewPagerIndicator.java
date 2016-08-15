package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.UIUtils;

import java.lang.reflect.Type;

public class SimpleViewPagerIndicator extends LinearLayout {

    private static final int COLOR_TEXT_NORMAL = Color.parseColor("#686868");
    private static final int COLOR_INDICATOR_COLOR = Color.parseColor("#5cd0c2");
    private boolean isShowPop = false;//默认不弹出pop
    private String[] mTitles;
    private int mTabCount;
    private int mIndicatorColor = COLOR_INDICATOR_COLOR;
    private float mTranslationX;
    private Paint mPaint = new Paint();
    private int mTabWidth;
    private int childMargin = 240;
    private ViewPager viewPager;

    public SimpleViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(9.0F);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = (w - childMargin * (mTabCount - 1)) / mTabCount;
    }


    /**
     * 点击展示pop
     */
    public void changeToShowPop() {
        this.isShowPop = true;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        mTabCount = titles.length;
        generateTitleView();

    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }

    public int getChildMargin() {
        return childMargin;
    }

    public void setChildMargin(int childMargin) {
        this.childMargin = childMargin;
    }

    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */

        mTranslationX = (getWidth() + childMargin * (mTabCount - 1)) / mTabCount * (position + offset);

        invalidate();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;

        setWeightSum(count);
        for (int i = 0; i < count; i++) {
            final TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0,
                    LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            if (i > 0) {
                lp.leftMargin = childMargin;
            }
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(COLOR_TEXT_NORMAL);
            tv.setText(mTitles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            if (i == 1 && isShowPop) {
                RelativeLayout relativeLayout=new RelativeLayout(getContext());
                ImageView imageView=new ImageView(getContext());
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(24,24);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.bottomMargin=10;
                RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                imageView.setImageResource(R.drawable.activity_my_orders_cursor);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(layoutParams);
                relativeLayout.setLayoutParams(lp);
                relativeLayout.addView(imageView);
                tv.setLayoutParams(layoutParams2);
                relativeLayout.addView(tv);
                addView(relativeLayout);
            }else {
                tv.setLayoutParams(lp);
            }
            final int clickPosition = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(clickPosition, false);
                    if (clickPosition == 1 && isShowPop) {//我的订单中的一个页面需要展示pop
                        if (onPopShowListener != null) {
                            onPopShowListener.onShow(tv);
                        }
                    }


                }
            });
            if (!(i == 1 && isShowPop)) {
                addView(tv);
            }
        }
    }

    public interface OnPopShowListener {
        void onShow(TextView tv);
    }

    private OnPopShowListener onPopShowListener;

    public void setOnPopShowListener(OnPopShowListener onPopShowListener) {
        this.onPopShowListener = onPopShowListener;
    }
}
