package com.yunspeak.travel.ui.baseui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.CommonDataBinding;
import com.yunspeak.travel.global.ListBean;
import com.yunspeak.travel.ui.fragment.BaseLoadingFragment;
import com.yunspeak.travel.ui.view.StatusView;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;
import java.lang.reflect.ParameterizedType;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyang on 2017/3/17.
 * 基类 加载 刷新
 */

public abstract class BaseLoadAndRefreshFragment<E extends ListBean<T>, T> extends BaseLoadingFragment{
    protected BasePullAndRefreshModel<T> basePullAndRefreshModel = null;
    protected SwipeToLoadLayout swipeToLoadLayout;
    protected boolean isRefresh;
    private boolean isFirst = true;
    protected RecyclerView recyclerView;
    protected CommonDataBinding commonDataBinding;


    @Override
    protected void childLoad() {
        if (basePullAndRefreshModel == null) {
            basePullAndRefreshModel = initModel();
        }
        basePullAndRefreshModel.onLoadAuto(statusView, getTInstance(), new Consumer<E>() {
            @Override
            public void accept(@NonNull E datas) throws Exception {
                if (isFirst) {
                    swipeToLoadLayout = (SwipeToLoadLayout) statusView.findViewById(R.id.status_content_view);
                    recyclerView = (RecyclerView) statusView.findViewById(R.id.swipe_target);
                    if (swipeToLoadLayout != null) {
                        basePullAndRefreshModel.setOnLoadListener(new BasePullAndRefreshModel.OnLoadListener() {
                            @Override
                            public void onLoad(boolean isRefresh) {
                                BaseLoadAndRefreshFragment.this.isRefresh=isRefresh;
                                childLoad();
                            }
                        });
                    }
                    onReceive(datas);
                    if (recyclerView!=null){
                        basePullAndRefreshModel.setRecycleView(recyclerView,datas.getData());
                    }
                    isFirst = false;
                    return;
                }

                if (isRefresh) {
                    basePullAndRefreshModel.refresh(datas.getData());
                } else {
                    basePullAndRefreshModel.loadMore(datas.getData());
                }

            }
        },isRefresh);

    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        commonDataBinding = DataBindingUtil.inflate(inflater, R.layout.common_recycle_databinding_layout, container, false);
        return commonDataBinding.getRoot();
    }

    protected void onReceive(E datas) {
        commonDataBinding.setBase(basePullAndRefreshModel);
        commonDataBinding.setManagerType(0);//默认的样式为刷新加载样式
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        statusView.setOnErrorBackListener(new StatusView.OnErrorBackListener() {
            @Override
            public boolean onErrorBack(Throwable throwable) {
                ToastUtils.showToast(!NetworkUtils.isNetworkConnected()?getString(R.string.network_isnot_available):throwable.getMessage());
                if (swipeToLoadLayout!=null){
                    swipeToLoadLayout.setLoadingMore(false);
                    swipeToLoadLayout.setRefreshing(false);
                }
                return false;
            }
        });
    }

    protected abstract BasePullAndRefreshModel<T> initModel();

    /**
     * 实例化 T
     *
     * @return T的 class类型
     */
    protected Class<E> getTInstance() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[0];
    }
}
