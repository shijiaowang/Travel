package com.yunspeak.travel.ui.view;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.TypefaceUtis;

public class FontsIconViewPagerIndicator extends LinearLayout
{
   private String[] mTitles;
	private String[] mIcon=new String[]{"棟","姬","钳"};
	private static final int COLOR_TEXT_NORMAL = Color.parseColor("#646464");
	private static final int COLOR_INDICATOR_COLOR =Color.parseColor("#ffc684");


	private int mTabCount;
	private int mIndicatorColor = COLOR_INDICATOR_COLOR;
	private float mTranslationX;
	private Paint mPaint = new Paint();
	private int mTabWidth;
	private ViewPager viewPager;

	public FontsIconViewPagerIndicator(Context context)
	{
		this(context, null);
	}

	public FontsIconViewPagerIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mPaint.setColor(mIndicatorColor);
		mPaint.setStrokeWidth(9.0F);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mTabWidth = w / mTabCount;
	}

	public void setTitles(String[] titles)
	{
		mTitles = titles;
		mTabCount = titles.length;
		//generateTitleView();
		generateIconTitleView();

	}

	public void setIndicatorColor(int indicatorColor)
	{
		this.mIndicatorColor = indicatorColor;
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(mTranslationX, getHeight() - 2);
		canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
		canvas.restore();
	}

	public void scroll(int position, float offset)
	{
		/**
		 * <pre>
		 *  0-1:position=0 ;1-0:postion=0;
		 * </pre>
		 */
		mTranslationX = getWidth() / mTabCount * (position + offset);
		invalidate();
	}

    public void setTagClick(ViewPager viewPager){
		this.viewPager=viewPager;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return super.dispatchTouchEvent(ev);
	}
	private void generateIconTitleView()
	{
		if (getChildCount() > 0)
			this.removeAllViews();
		int count = mTitles.length;
		setWeightSum(count);
		for (int i = 0; i < count; i++)
		{
			LinearLayout lv = new LinearLayout(getContext());
			LayoutParams lp = new LayoutParams(0,
					LayoutParams.MATCH_PARENT);
			lp.weight = 1;
			lv.setOrientation(HORIZONTAL);
			lv.setGravity(Gravity.CENTER);
			lv.setLayoutParams(lp);
			for (int j=0;j<2;j++){
				TextView textView=new TextView(getContext());
				textView.setTextColor(getResources().getColor(R.color.color646464));
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
				LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				if (j==0){
					textView.setTypeface(TypefaceUtis.getTypeface(getContext()));
					textView.setText(mIcon[i]);

				}else {
					tvLp.leftMargin=20;
					textView.setText(mTitles[i]);
				}
				textView.setLayoutParams(tvLp);
				lv.addView(textView);
			}
			final int finalI = i;
			lv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (viewPager != null) {
						viewPager.setCurrentItem(finalI, false);
					}
				}
			});
			addView(lv);
		}
	}

	private void generateTitleView()
	{
		if (getChildCount() > 0)
			this.removeAllViews();
		int count = mTitles.length;

		setWeightSum(count);
		for (int i = 0; i < count; i++)
		{
			TextView tv = new TextView(getContext());
			LayoutParams lp = new LayoutParams(0,
					LayoutParams.MATCH_PARENT);
			lp.weight = 1;
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(COLOR_TEXT_NORMAL);
			tv.setText(mTitles[i]);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			tv.setLayoutParams(lp);
			final int finalI = i;
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (viewPager != null) {
						viewPager.setCurrentItem(finalI, false);
					}
				}
			});
			addView(tv);
		}
	}

}
