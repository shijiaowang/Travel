package com.yunspeak.travel.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IStatusChange;
import com.yunspeak.travel.ui.view.StatusView;

/**
 * Created by wangyang on 2017/3/9.
 * 基类
 */

public abstract class BaseLoadingFragment extends Fragment {

    private boolean isVisible;
    private boolean isPrepared;
    boolean isFirstLoad=false;
    protected StatusView statusView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(initLayoutRes(), container, false);
        statusView = (StatusView) inflate.findViewById(R.id.status_view);
        isPrepared = true;
        initOptions();
        return inflate;
    }

    /**
     * 可以做一些操作
     */
    protected  void initOptions(){

    }

    /**
     * 布局 id
     * @return 布局 id
     */
    protected abstract int initLayoutRes();


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());

    }

    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onLoad();
        }else {
            isVisible=false;
        }
    }

    private void onLoad() {
        if (isPrepared && isVisible) {
            statusView.showLoadingView(isFirstLoad);
            if (!isFirstLoad) isFirstLoad = true;
            childLoad();
        }

    }

    /**
     * 子view的网络操作
     */
    protected abstract void childLoad();


}
