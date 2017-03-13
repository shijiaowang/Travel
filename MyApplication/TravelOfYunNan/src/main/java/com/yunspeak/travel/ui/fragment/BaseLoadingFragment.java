package com.yunspeak.travel.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.umeng.analytics.MobclickAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.view.StatusView;


/**
 * Created by wangyang on 2017/3/9.
 * 基类
 */

public abstract class BaseLoadingFragment extends Fragment {

    private boolean isVisible;
    private boolean isPrepared;
    boolean isFirstLoad=true;
    protected StatusView statusView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=initRootView(inflater,container);
        statusView = (StatusView) rootView.findViewById(R.id.status_view);
        statusView.setOnTryGetDataListener(new StatusView.OnTryGetDataListener() {
            @Override
            public void onTryGet() {
                childLoad();
            }
        });
        isPrepared = true;
         onLoad();
        initOptions();
        return rootView;
    }

    protected abstract View initRootView(LayoutInflater inflater, ViewGroup container);

    /**
     * 可以做一些操作
     */
    protected  void initOptions(){

    }

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
        if (isVisible && isPrepared) {
            statusView.showLoadingView(isFirstLoad);
            if (!isFirstLoad) isFirstLoad = false;
            childLoad();
        }
    }

    /**
     * 子view的网络操作
     */
    protected abstract void childLoad();


}
