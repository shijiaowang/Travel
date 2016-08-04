package com.example.administrator.travel.ui.activity;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.TypefaceUtis;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 带有相同头布局
 */
public abstract class BarBaseActivity extends BaseActivity {
    private static final float CHANGE_COLOR_LIMIT = 600f;//设置变色区间
    @ViewInject(R.id.tv_back)
    private TextView mTvBack;
    @ViewInject(R.id.tv_name)
    private TextView mTitleName;
    @ViewInject(R.id.bg_1)
    private View mBg1;

    private int normalBgColor = Color.parseColor("#5cd0c2");
    private float alpha = 0f;
    @ViewInject(R.id.vs_content)
    private ViewStub mVsContent;
    private ViewStub mVsRightIcon;
    private SlippingScrollView mSsvScroll;

    @Override
    protected int initLayoutRes() {
        if (rootIsLinearLayout()) {
            return R.layout.activity_bar_base_ll;//竖直布局
        }
        return R.layout.activity_bar_base_rl;//重叠布局
    }

    protected boolean rootIsLinearLayout() {
        return true;
    }

    /**
     * 获取背景
     *
     * @return
     */
    public View getmBg1() {
        return mBg1;
    }

    @Override
    protected void initView() {
        if (leftIsFontIcon()) {
            mTvBack.setTypeface(TypefaceUtis.getTypeface(this));
        }else {
            mTvBack.setText(getLeftText());
        }
        mTvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, getLeftTextSize());
        mTitleName.setText(setTitleName());
        mVsContent.setLayoutResource(setContentLayout());
        mVsContent.inflate();
        if (haveRightIcon()) {
            mVsRightIcon = (ViewStub) findViewById(R.id.vs_right_icon);
        }
        if (canScrollToChangeTitleBgColor()) {
            mSsvScroll = (SlippingScrollView) findViewById(R.id.ssv_scroll);
        }
        x.view().inject(this);
        initContentView();
    }

    /**
     * 默认返回左边的大小
     *
     * @return
     */
    protected float getLeftTextSize() {
        return 25;
    }

    protected String getLeftText() {
        return "页面";
    }

    /**
     * 左边的是不是文字
     *
     * @return
     */
    protected boolean leftIsFontIcon() {
        return true;
    }

    /**
     * 是否能够跟随滑动改变背景颜色，一定要有SlippingScrollView且id固定不变！
     *
     * @return
     */
    protected boolean canScrollToChangeTitleBgColor() {
        return false;
    }

    protected boolean haveRightIcon() {
        return false;
    }

    /**
     * 如果需要动态添加右边的图标
     *
     * @return
     */
    public ViewStub getmVsRightIcon() {
        return mVsRightIcon;
    }

    /**
     * 初始化布局
     */
    protected abstract void initContentView();

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setContentLayout();
    @Event(R.id.tv_back)
    private void onClickFinshView(View view){
        finish();
    }
    @Override
    protected void initListener() {
        if (canScrollToChangeTitleBgColor() && mSsvScroll != null) {
            mSsvScroll.setSlippingListener(new SlippingScrollView.SlippingListener() {
                @Override
                public void slipping(int l, int i, int oldl, int t) {
                    getmBg1().setAlpha(Math.abs(t / CHANGE_COLOR_LIMIT) > 1 ? 1f : Math.abs(t / CHANGE_COLOR_LIMIT));
                }
            });
        }
        initEvent();
    }

    protected abstract void initEvent();


    @Override
    protected void initData() {
        mTitleName.setText(setTitleName());
        mBg1.setBackgroundColor(getBgColor());
        mBg1.setAlpha(getAlpha());

        initViewData();
    }


    /**
     * 初始化数据
     */
    protected abstract void initViewData();


    /**
     * 设置标题
     *
     * @return
     */
    protected abstract String setTitleName();


    /**
     * 背景颜色
     *
     * @return
     */
    public int getBgColor() {
        return normalBgColor;
    }

    /**
     * 获取背景透明度
     *
     * @return
     */
    public float getAlpha() {
        return alpha;
    }


}
