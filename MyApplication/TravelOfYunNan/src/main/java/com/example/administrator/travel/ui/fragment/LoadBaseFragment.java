package com.example.administrator.travel.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.ui.view.LoadingPage;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2016/8/3 0003.
 */
public abstract class LoadBaseFragment extends Fragment {
    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;
    private Fragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = registerEvent();
        if (fragment != null) {
            registerEventBus(fragment);
        }

    }

    protected abstract Fragment registerEvent();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getContext()) {
            @Override
            public View onCreateSuccessView() {
                return initView();
            }

            @Override
            public void onLoad() {
                LoadBaseFragment.this.load();
            }

            /**
             * 子类中修改
             * @return
             */
            @Override
            public ResultState changeState() {

                return LoadBaseFragment.this.getCurrentState();
            }
        };
        return loadingPage;

    }


    private void load() {
        onLoad();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (fragment!=null){
            unregisterEventBus(fragment);
            fragment=null;
        }
    }

    public void registerEventBus(Fragment f) {
        if (!EventBus.getDefault().isRegistered(f)) {
            EventBus.getDefault().register(f);
        }

    }

    public void unregisterEventBus(Fragment f) {
        if (EventBus.getDefault().isRegistered(f)) {
            EventBus.getDefault().unregister(f);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initContentView();
        initListener();
        initLoad();
    }


    /**
     * 初次加载数据
     */
    protected abstract void initLoad();

    public void loadData() {
        if (loadingPage != null) {
            loadingPage.loadData();
        }
    }

    /**
     * 初始化布局view
     */
    protected abstract void initContentView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 发起网络请求，
     */
    protected abstract void onLoad();

    /**
     * 跟布局
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 设置读取状态
     *
     * @param state
     */
    public void setState(LoadingPage.ResultState state) {
        currentState = state;
    }

    /**
     * 获取网络状态
     */
    public LoadingPage.ResultState getCurrentState() {
        return currentState;
    }

    /**
     * eventbus返回时调用
     */
    public void afterLoadData() {

        if (loadingPage != null) {
            loadingPage.afterLoadData();
        }
    }
}
