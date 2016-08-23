package com.example.administrator.travel.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.utils.LogUtils;

import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public abstract class LoadBaseFragment extends Fragment {
    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;
    private boolean childIsResume=false;


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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (childIsResume) {
                        onLoad();
                        break;
                    }
                }
            }
        }).start();


    }
    public void registerEventBus(Fragment f){
        EventBus.getDefault().register(f);
        this.childIsResume=true;
    }
    public void unregisterEventBus(Fragment f){
        EventBus.getDefault().unregister(f);
        this.childIsResume=false;
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
