package com.yunspeak.travel.ui.baseui;

import android.support.v7.widget.RecyclerView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.ListBean;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
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
    BasePullAndRefreshModel<T> basePullAndRefreshModel = null;
    private SwipeToLoadLayout swipeToLoadLayout;
    private boolean isRefresh;
    private boolean isFirst = true;
    private RecyclerView recyclerView;


    @Override
    protected void childLoad() {
        if (basePullAndRefreshModel == null) {
            basePullAndRefreshModel = initModel();
        }
        //关联adapter
        if (basePullAndRefreshModel.getCommonRecycleViewAdapter()==null && recyclerView!=null && recyclerView.getAdapter()!=null){
            basePullAndRefreshModel.setCommonRecycleViewAdapter((CommonRecycleViewAdapter<T>) recyclerView.getAdapter());
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

    protected abstract void onReceive(E datas);

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
