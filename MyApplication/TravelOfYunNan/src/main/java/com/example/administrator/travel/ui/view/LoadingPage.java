package com.example.administrator.travel.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.UIUtils;

/**
 * Created by android on 2016/3/15.
 * 提取�?有fragment共有的状态，根据状�?�显示不同页面的自定义控�?
 * -未加载，-加载中，-加载失败�?-数据为空,-加载成功
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNLOAD = 0;//未加载
    private static final int STATE_LOAD_LOADING = 1;//加载中
    private static final int STATE_LOAD_ERROR = 2;//加载错误
    private static final int STATE_LOAD_SUCCESS = 3;//加载成功
    private int mCurrentState = STATE_LOAD_UNLOAD;//当前
    private int mPreState = STATE_LOAD_UNLOAD;//上一次
    private TextView loadingView;
    private ImageView errorView;
    private View successView;

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
            loadingView = new TextView(getContext());
            loadingView.setText("加载中");
            addView(loadingView);//添加加载页面
        }
        //加载错误页面
        if (errorView == null) {
            errorView= (ImageView) UIUtils.inflate(R.layout.page_error);
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重新加载数据
                    mCurrentState=STATE_LOAD_UNLOAD;
                    loadData();
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
        //这是加载成功的界面
        if (mCurrentState == STATE_LOAD_SUCCESS && successView == null) {
            successView = onCreateSuccessView();
            //不为空加
            if (successView != null && successView.getParent()==null) {
                addView(successView);
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
    public void loadData() {
        if (mCurrentState != STATE_LOAD_LOADING) {
            mCurrentState = STATE_LOAD_LOADING;
            onLoad();
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
        if (mPreState != mCurrentState) {
            showRightView();
            mPreState = mCurrentState;
        } else {
            mCurrentState = STATE_LOAD_UNLOAD;
        }
    }


    /**
     * 加载成功的方法，交给调用者去实现
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 加载网络数据的方法，交给调用者去实现
     */
    public abstract void onLoad();

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
        STATE_ERROR(STATE_LOAD_ERROR);
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
}
