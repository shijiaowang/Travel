package com.example.administrator.travel.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/3 0003.
 */
public abstract class LoadBaseFragment extends Fragment implements XListView.IXListViewListener{
    public static final int FIRST_REFRESH = 0;
    public static final int LOAD_MORE = 1;
    public static final int REFRESH = 2;

    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;
    private Fragment fragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = registerEvent();
        if (fragment != null) {
            registerEventBus(fragment);
            LogUtils.e("fragment的Event注册了");
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if (fragment == null) {
            fragment = registerEvent();
            if (fragment==null)return;
            registerEventBus(fragment);
            LogUtils.e("fragment的Event注册了");
        }
    }

    protected abstract Fragment registerEvent();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getContext()) {
            @Override
            public View onCreateSuccessView() {


                return    initView();
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
        onLoad(REFRESH);//第一次加载,使用刷新
    }
   protected int getListSize(List list){
       if (list==null)return 0;
       return list.size();
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fragment != null) {
            unregisterEventBus(fragment);
            LogUtils.e("fragment的Event注销了");
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
        initListener();
        loadData();
    }


    public void loadData() {
        if (loadingPage != null) {
            loadingPage.loadData();
        }
    }


    /**
     * 跟布局
     *
     * @return
     */
    protected abstract View initView();



    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 发起网络请求，
     */
    protected abstract void onLoad(int type);


    /**
     * 设置读取状态
     *
     * @param state
     */
    public void setState(LoadingPage.ResultState state) {
        currentState = state;
        afterLoadData();
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

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
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
    @Override
    public void onRefresh() {
        onLoad(REFRESH);
    }

    @Override
    public void onLoadMore() {
        onLoad(LOAD_MORE);
    }
}
