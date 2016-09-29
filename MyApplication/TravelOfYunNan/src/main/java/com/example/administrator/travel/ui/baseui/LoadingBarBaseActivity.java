package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.me.messagecenter.MessageCommonEvent;
import com.example.administrator.travel.ui.view.SlippingScrollView;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.TypefaceUtis;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/25 0025.
 * 带有相同头布局,网络加载
 */
public abstract class LoadingBarBaseActivity<T extends HttpEvent> extends BaseActivity implements XListView.IXListViewListener, XScrollView.IXScrollViewListener {
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
    private View root;

    public TextView getmTvRightIcon() {
        return mTvRightIcon;
    }

    private int normalBgColor = Color.parseColor("#5cd0c2");
    private float alpha = 0f;
    @ViewInject(R.id.fl_content)
    private FrameLayout mFlContent;
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
        root = View.inflate(this, setContentLayout(), null);
        root.setVisibility(View.GONE);
        mFlContent.addView(root);
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
                    if (scrollListener!=null){
                        scrollListener.percent(Math.abs(t/CHANGE_COLOR_LIMIT));
                    }
                }
            });
        }
        if (canScrollToChangeTitleBgColor() && xScrollView != null) {
            xScrollView.setSlippingListener(new XScrollView.SlippingListener() {
                @Override
                public void slipping(int l, int i, int oldl, int t) {
                    LogUtils.e("t=" + t + "i=" + i + "l=" + l + "oldl" + oldl);
                    if (xScrollView.isTop()) {//解决 下拉刷新引发title变色问题
                        t=0;
                    }
                    if (scrollListener!=null){
                        scrollListener.percent(Math.abs(t/CHANGE_COLOR_LIMIT));
                    }

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
        onLoad(TYPE_REFRESH);
    }


    protected void setIsProgress(boolean show) {
        mPbProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        root.setVisibility(View.VISIBLE);
    }

    protected void setIsError(boolean show) {
        if (mIvError == null) {
            mIvError = (ImageView) View.inflate(this,R.layout.page_error,null);
            mFlContent.addView(mIvError);
            mIvError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIvError.setVisibility(View.GONE);
                    onLoad(TYPE_REFRESH);
                    root.setVisibility(View.VISIBLE);
                    setIsProgress(true);
                }
            });
        }
        mIvError.setVisibility(show ? View.VISIBLE : View.GONE);
        root.setVisibility(show?View.GONE:View.VISIBLE);
    }

    protected abstract void onLoad(int type);

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
        if (xListView==null)return;
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }
    protected void loadEnd(XScrollView xListView) {
        if (xListView==null)return;
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * 初始化XlistView
     * @param listView
     * @param canPull 是否能下拉刷新
     * @param canLoadMore 是否可以LoadMore
     */
    protected void initXListView(XListView listView, boolean canPull, boolean canLoadMore){
        listView.setPullLoadEnable(canLoadMore);
        listView.setPullRefreshEnable(canPull);
        listView.setXListViewListener(this);
        listView.setRefreshTime(getTime());
    }

   protected int getListSize(List list){
       if (list==null)return 0;
       return list.size();
   }

    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
      onLoad(TYPE_LOAD);
    }
    private ScrollListener scrollListener;


    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface ScrollListener{
        void percent(float percent);
    }
    @Subscribe
    public void onEvent(HttpEvent event){
        setIsProgress(false);
        if (getxScrollView()!=null){
            loadEnd(getxScrollView());
        }
       if (event.isSuccess()){
           try {
               isSuccessed = true;
               T t = (T) event;
               onSuccess(t);
           }catch (Exception e){
               e.printStackTrace();
               LogUtils.e("出现异常了");
           }
       }else {
           ToastUtils.showToast(event.getMessage());
           onFail(event);
       }

    }

    /**
     * 处理失败的消息
     * @param event
     */
    private boolean isSuccessed=false;
    protected  void onFail(HttpEvent event){
        if (!isSuccessed){
            setIsError(true);
        }
    }
    protected void initXScrollView(boolean pull,boolean load){
        if (getxScrollView()==null)return;
        xScrollView.setPullRefreshEnable(pull);
        xScrollView.setPullLoadEnable(load);
        xScrollView.setIXScrollViewListener(this);
        xScrollView.setRefreshTime(getTime());
    }

    /**
     * 链接网络后处理成功的消息
     * @param t
     */
    protected abstract void onSuccess(T t);
}
