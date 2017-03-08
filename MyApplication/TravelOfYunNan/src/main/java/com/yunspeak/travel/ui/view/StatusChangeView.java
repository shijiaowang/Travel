package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yunspeak.travel.R;


/**
 * Created by wangyang on 2017/3/8.
 * 同意正确错误加载页面管理
 */

public class StatusChangeView {
    private static final int STATE_LOAD_UNLOAD = -1;//未加载
    private static final int STATE_LOAD_SUCCESS = 0;//加载成功
    private static final int STATE_LOAD_ERROR = 1;//加载错误
    private static final int STATE_LOAD_LOADING = 2;//加载中
    private static final int STATE_LOAD_EMPTY = 3;//数据为空
    private int mCurrentState = STATE_LOAD_UNLOAD;//当前
    private View[] childView = new View[4];
    private int loadingId;
    private int emptyId;
    private int successId;
    private int contentId;
    private int errorId;
    private View loadingView;//加载页
    private View errorView;//错误也
    private View successView;//成功页面
    private View emptyView;//空页面
    private View contentView;// //contentView在successView之中，
    private ViewGroup rootView;//所有页面的父亲
    private final LayoutInflater layoutInflater;

    private StatusChangeView(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.status_layout, null);
    }
   public void setStatus(int status){
       setStatus(status,true);
   }
    /**
     * @param status        当前的状态
     * @param isHideContent 是否隐藏内容页
     */
    public void setStatus(int status, boolean isHideContent) {
        if (status < 0 || status > childView.length - 1) {
            return;
        }
        if (childView[status] == null) {
            View view = null;
            switch (status) {

                case STATE_LOAD_LOADING:
                    view = loadingView = inflateView(loadingId);
                    break;
                case STATE_LOAD_EMPTY:
                    view = emptyView = inflateView(emptyId);
                    break;
                case STATE_LOAD_ERROR:
                    view = errorView = inflateView(errorId);
                    break;
                case STATE_LOAD_SUCCESS:
                    view = successView = inflateView(successId);
                    if (successId != contentId) {
                        //不等于的话说明需要其他页面需要隐藏只是主页面的一部分
                        view = contentView.findViewById(contentId);
                    }
                    break;
            }
            childView[status]=view;
            addViewInRootView(view);
        }
        for (View view : childView) {
            if (view == childView[status]) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(isHideContent ? View.GONE : status == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
            }
        }
    }

    /**
     * 获取最终页面
     *
     * @return
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * 填充
     *
     * @param id
     * @return
     */
    public View inflateView(int id) {
        if (id != 0) {
            try {
                return layoutInflater.inflate(id, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 添加View进入rootView
     */
    private void addViewInRootView(View view) {
            if (view != null) {
                rootView.addView(loadingView);
            }

    }

    public static class Builder {
        private int loadingId;
        private int emptyId;
        private int successId;
        private int contentId;
        private int errorId;


        public Builder setLoadingView(int loadingViewId) {
            this.loadingId = loadingViewId;
            return this;
        }

        public Builder setErrorView(int errorViewId) {
            this.errorId = errorViewId;
            return this;
        }


        public Builder setSuccessView(int successViewId) {
            this.successId = successViewId;
            return this;
        }


        public Builder setEmptyView(int emptyViewId) {
            this.emptyId = emptyViewId;
            return this;
        }


        public Builder setContentView(int contentViewId) {
            this.contentId = contentViewId;
            return this;
        }

        public StatusChangeView build(Context context) {
            StatusChangeView statusChangeView = new StatusChangeView(context);
            statusChangeView.contentId = contentId;
            statusChangeView.errorId = errorId;
            statusChangeView.emptyId = emptyId;
            statusChangeView.successId = successId;
            statusChangeView.loadingId = loadingId;
            return statusChangeView;

        }


    }


}
