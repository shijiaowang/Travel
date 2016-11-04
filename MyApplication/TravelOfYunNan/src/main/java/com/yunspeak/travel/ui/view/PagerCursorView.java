package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.utils.DensityUtils;

import org.w3c.dom.Attr;
import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by wangyang on 2016/11/4 0004.
 * 小圆点指示器
 */

public class PagerCursorView extends RelativeLayout {


    private int height = DensityUtil.dip2px(6);
    private LinearLayout mLlRoot;
    private View mVDot;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int position = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(position, true);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
    private int mPointDistance;
    private int mFirstDotLeft;
    public  int childCount;
    private ViewPager viewPager;

    public PagerCursorView(Context context) {
        super(context);
        init(context);

    }

    public PagerCursorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PagerCursorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pager_cursor, this, false);
        this.addView(inflate);
        mLlRoot = (LinearLayout) inflate.findViewById(R.id.ll_indicator);
        mVDot = inflate.findViewById(R.id.v_dot);
    }

    public void setViewPager(ViewPager viewPager, int count, boolean isAutoMove) {
        if (count == 0) return;
        this.viewPager = viewPager;
        childCount = count;
        for (int i = 0; i < childCount; i++) {
            View view = new View(getContext());
            view.setBackgroundResource(R.drawable.dot_for_viewpager_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height, height);
            if (i > 0) {
                params.leftMargin = DensityUtils.dipToPx(getContext(), 11);
            }
            view.setLayoutParams(params);
            mLlRoot.addView(view);
        }
        /**
         * 绘制完成回调
         */
        mLlRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPointDistance = mLlRoot.getChildAt(1).getLeft() - mLlRoot.getChildAt(0).getLeft();
                //获取第一个的左边
                mFirstDotLeft = mLlRoot.getChildAt(0).getLeft();
                //初始化小圆点
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                layoutParams.leftMargin = mFirstDotLeft;
                mVDot.setLayoutParams(layoutParams);
                //移除监听事件
                mLlRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        int position = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % childCount;
        viewPager.setCurrentItem(position, false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (pagerOnChangeListener != null) {
                    pagerOnChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                int rightPosition = position % childCount;
                if (rightPosition < childCount - 1) {
                    //动态改变小红点的值
                    float len = mPointDistance * positionOffset + mPointDistance * rightPosition;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                    layoutParams.leftMargin = (int) (len + mFirstDotLeft);
                    //Utils.ShowToast(MainActivity.this,len+"");
                    mVDot.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (pagerOnChangeListener != null) {
                    pagerOnChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (pagerOnChangeListener != null) {
                    pagerOnChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

        if (isAutoMove) {
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    private PagerOnChangeListener pagerOnChangeListener;

    public void setPagerOnChangeListener(PagerOnChangeListener pagerOnChangeListener) {
        this.pagerOnChangeListener = pagerOnChangeListener;
    }

    public interface PagerOnChangeListener {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    public static abstract class CursorPagerAdapter<T> extends PagerAdapter {
        protected List<T> data;

        public CursorPagerAdapter(List<T> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return inflateView(container,position%data.size());
        }

        public abstract Object inflateView(ViewGroup container, int position);
    }
}
