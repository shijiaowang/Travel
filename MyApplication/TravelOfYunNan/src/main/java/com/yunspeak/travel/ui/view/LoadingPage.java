package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2016/3/15.
 * 提取�?有fragment共有的状态，根据状�?�显示不同页面的自定义控�?
 * -未加载，-加载中，-加载失败�?-数据为空,-加载成功
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNLOAD = 0;//未加载
    private static final int STATE_LOAD_LOADING = 1;//加载中
    private static final int STATE_LOAD_ERROR = 2;//加载错误
    private static final int STATE_LOAD_SUCCESS = 3;//加载成功
    private static final int STATE_LOAD_EMPTY = 4;//数据为空
    private int mCurrentState = STATE_LOAD_UNLOAD;//当前
    private View loadingView;
    private ImageView errorView;
    private View successView;
    private View emptyView;
    private View floatLoading;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始�?
     */
    private void initView() {
        //加载读取页面
        if (loadingView == null) {
             loadingView =  UIUtils.inflate(R.layout.loading_view);
            addView(loadingView);//添加加载页面
        }
        //加载读取页面
        if (emptyView == null) {
            emptyView = UIUtils.inflate(R.layout.empty_view);
            emptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData(IState.TYPE_REFRESH);
                }
            });
            addView(emptyView);//添加加载页面
        }
        //加载错误页面
        if (errorView == null) {
            errorView= (ImageView) UIUtils.inflate(R.layout.page_error);
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重新加载数据
                    mCurrentState=STATE_LOAD_UNLOAD;
                    loadData(LoadBaseFragment.TYPE_REFRESH);
                }
            });
            addView(errorView);
        }
        //显示页面
        showRightView();
    }


    private void showRightView() {
        //是否展示加载中页�?
        loadingView.setVisibility((mCurrentState == STATE_LOAD_UNLOAD || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE : View.GONE);
        //是否展示错误页面
        errorView.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        if (floatLoading!=null)floatLoading.setVisibility(GONE);
        //这是加载成功的界面
        if (mCurrentState == STATE_LOAD_SUCCESS && successView == null) {
            successView = onCreateSuccessView();
            //不为空加
            if (successView != null && successView.getParent()==null) {
                addView(successView);
                if (floatLoading == null) {
                    floatLoading = UIUtils.inflate(R.layout.loading_view);
                    floatLoading.setVisibility(GONE);
                    addView(floatLoading);//添加加载页面,悬浮于成功页面之上
                }
            }
        }
        //是否显示页面
        if (successView != null) {
            successView.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }
    /**
     * 发送网络请求
     */
    public void loadData(int type) {
        if (mCurrentState != STATE_LOAD_LOADING) {
            mCurrentState = STATE_LOAD_LOADING;
            loadingView.setVisibility(VISIBLE);
            errorView.setVisibility(GONE);
            emptyView.setVisibility(GONE);
            onLoad(type);
        }
    }

    /**
     * 发送网络请求之后
     */
    public void afterLoadData(){
        showPage();
    }

    private void showPage() {
        ResultState resultState = changeState();
        if (resultState != null) {
            mCurrentState = resultState.getState(); //获取返回的结�?,更新网络状�??
        }
        showRightView();
    }


    /**
     * 加载成功的方法，交给调用者去实现
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 加载网络数据的方法，交给调用者去实现
     * @param type
     */
    public abstract void onLoad(int type);

    /***
     * 由于使用了evenBus 所以分两步使用，onload单纯的发布网络请求
     * on event之中动态修改返回的状态
     *
     * @return
     */
    public abstract ResultState changeState();

    /**
     * 加载网络数据枚举
     */
    public enum ResultState {
        //成功 失败 与数据为�?
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_ERROR(STATE_LOAD_ERROR),
        STATE_EMPTY(STATE_LOAD_EMPTY);
        private int state;

        /**
         * 带构造函数的枚举，目的与状�?�绑定，方便进行ui更新判断
         *
         * @param state
         */
        ResultState(int state) {
            this.state = state;
        }

        /**
         * 获取当前状�??
         */
        public int getState() {
            return state;
        }

    }
    public void setLoading(boolean isLoading){
        floatLoading.setVisibility(VISIBLE);
    }
}
