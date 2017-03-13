package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.utils.DensityUtils;

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
            boolean isShow = fragment != null && fragment.getUserVisibleHint();
            if (!breakAutoSlide && isShow) {
                int position = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(position, true);
            }
            mHandler.sendEmptyMessageDelayed(0,5000);
        }
    };
    private int mPointDistance;
    private int mFirstDotLeft;
    public int childCount;
    private ViewPager viewPager = null;
    private boolean breakAutoSlide=false;
    private Fragment fragment;
    private int prePosition;

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

    public void setViewPager(ViewPager viewPager, int count, boolean isAutoMove, Fragment fragment) {

        if (count < 2 || this.viewPager != null) {
            mVDot.setVisibility(GONE);
            return;//少于二
        }
        childCount = count;
        this.fragment = fragment;
        if (childCount >= 2) {
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
        }else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mVDot.getLayoutParams();
            layoutParams.gravity= Gravity.CENTER;
            mVDot.setLayoutParams(layoutParams);
        }
        this.viewPager = viewPager;
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        breakAutoSlide = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        breakAutoSlide = true;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        breakAutoSlide=false;
                        break;
                }
                return false;
            }
        });
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
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
                viewPager.setCurrentItem(childCount*100,false);
                prePosition=childCount*100;
            }
        });

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
                    movePoint(len);
                }

            }

            @Override
            public void onPageSelected(int position) {

                if (pagerOnChangeListener != null) {
                    pagerOnChangeListener.onPageSelected(position);
                }
                if (position%childCount==childCount-1 && prePosition==position+1){//第一个向左滑
                    float len = mPointDistance * (childCount-1);
                    movePoint(len);
                }
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (pagerOnChangeListener != null) {
                    pagerOnChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        if (isAutoMove) {
            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    }

    private void movePoint(float len) {
        LayoutParams layoutParams = (LayoutParams) mVDot.getLayoutParams();
        layoutParams.leftMargin = (int) (len + mFirstDotLeft);
        //Utils.ShowToast(MainActivity.this,len+"");
        mVDot.setLayoutParams(layoutParams);
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
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

         void onPageSelected(int position);

         void onPageScrollStateChanged(int state);
    }
    //databinding
    public static abstract class ObCursorPagerAdapter<T> extends PagerAdapter {
        protected ObservableArrayList<T> data;

        public ObCursorPagerAdapter(ObservableArrayList<T> data) {
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
            return inflateView(container, position % data.size());
        }

        public abstract Object inflateView(ViewGroup container, int position);
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
            return inflateView(container, position % data.size());
        }

        public abstract Object inflateView(ViewGroup container, int position);
    }
}
