package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.UIUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 带有相同头布局,网络加载
 */
public abstract class LoadingBarBaseActivity extends BaseActivity {
    private static final float CHANGE_COLOR_LIMIT = 600f;//设置变色区间
    @ViewInject(R.id.tv_back)
    private TextView mTvBack;
    @ViewInject(R.id.tv_name)
    private TextView mTitleName;
    @ViewInject(R.id.bg_1)
    private View mBg1;
    @ViewInject(R.id.pb_progress)
    private ProgressBar mPbProgress;
    @ViewInject(R.id.tv_right_icon)
    private TextView mTvRightIcon;

    public TextView getmTvRightIcon() {
        return mTvRightIcon;
    }

    private int normalBgColor = Color.parseColor("#5cd0c2");
    private float alpha = 0f;
    @ViewInject(R.id.vs_content)
    private ViewStub mVsContent;
    @ViewInject(R.id.vs_error)
    private ViewStub mVsError;
    private SlippingScrollView mSsvScroll;
    private XScrollView xScrollView;
    private ImageView mIvError;
    private Activity activity;

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
        } else {
            mTvBack.setText(getLeftText());
        }
        mTvBack.setTextSize(TypedValue.COMPLEX_UNIT_SP, getLeftTextSize());
        mTitleName.setText(setTitleName());
        mVsContent.setLayoutResource(setContentLayout());
        mVsContent.inflate();
        if (canScrollToChangeTitleBgColor()) {
            if (!isXScrollView()) {
                mSsvScroll = (SlippingScrollView) findViewById(R.id.ssv_scroll);
            } else {
                xScrollView = (XScrollView) findViewById(R.id.ssv_scroll);
            }
        }
        x.view().inject(this);

    }

    protected boolean isXScrollView() {
        return false;
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





    public void changeTitle(String title) {
        mTitleName.setText(title);
    }


    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int setContentLayout();

    @Event(R.id.tv_back)
    private void onClickFinshView(View view) {
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
        if (canScrollToChangeTitleBgColor() && xScrollView != null) {
            xScrollView.setSlippingListener(new XScrollView.SlippingListener() {
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
        activity = initViewData();
        if (activity != null) {
            registerEventBus(activity);
        }
        onLoad();
    }


    protected void setIsProgress(boolean show) {
        mPbProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    protected void setIsError(boolean show) {
        if (mIvError == null) {
            mVsError.inflate();
            mIvError = (ImageView) findViewById(R.id.iv_error);
            mIvError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIvError.setVisibility(View.GONE);
                    onLoad();
                }
            });
        }
        mIvError.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    protected abstract void onLoad();

    public XScrollView getxScrollView() {
        return xScrollView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (activity != null) {//避免退出界面注销
            registerEventBus(activity);
        }
    }

    /**
     * 初始化数据
     */
    protected abstract Activity initViewData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activity != null) {
            unregisterEventBus(this);
        }
    }

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

    protected void loadEnd(XListView xListView) {
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }
    protected void loadEnd(XScrollView xListView) {
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * 测量listview的高度
     *
     * @param mListView
     * @return
     */
    private int measureHeight(ListView mListView) {
        // get ListView adapter
        ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
    }

}
