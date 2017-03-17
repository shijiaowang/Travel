package com.yunspeak.travel.ui.baseui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.ListBean;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.fragment.BaseLoadingFragment;
import com.yunspeak.travel.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyang on 2017/3/17.
 * 基类 加载 刷新
 */

public abstract class BaseLoadAndRefreshFragment<E  extends ListBean<T>,T> extends BaseLoadingFragment implements OnLoadMoreListener, OnRefreshListener {
    BasePullAndRefreshModel<T> basePullAndRefreshModel=null;
    private SwipeToLoadLayout swipeToLoadLayout;
    private boolean isRefresh;
    private boolean isFirst=true;


    @Override
    protected void childLoad() {
        basePullAndRefreshModel=initModel();
        if (basePullAndRefreshModel!=null){
            basePullAndRefreshModel.onLoadAuto(statusView, getTInstance(), new Consumer<E>() {
                @Override
                public void accept(@NonNull E datas) throws Exception {
                    if (isFirst){
                        swipeToLoadLayout= (SwipeToLoadLayout) statusView.findViewById(R.id.status_content_view);
                        if (swipeToLoadLayout!=null){
                            basePullAndRefreshModel.setSwipeToLoadLayout(swipeToLoadLayout);
                            swipeToLoadLayout.setOnLoadMoreListener(BaseLoadAndRefreshFragment.this);
                            swipeToLoadLayout.setOnRefreshListener(BaseLoadAndRefreshFragment.this);
                        }
                        onReceive(datas);
                        isFirst=false;
                        statusView.cnaShow(false);
                        return;
                    }
                    if (isRefresh) {
                        basePullAndRefreshModel.refresh(datas.getData());
                    }else {
                        basePullAndRefreshModel.loadMore(datas.getData());
                    }

                }
            });
        }
    }




    protected abstract void onReceive(E datas);

    protected  abstract  BasePullAndRefreshModel<T> initModel();

    /**
     * 实例化 T
     *
     * @return T的 class类型
     */
    public Class<E> getTInstance() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<E>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void onLoadMore() {
        isRefresh=false;
        childLoad();

    }

    @Override
    public void onRefresh() {
        isRefresh=true;
        childLoad();

    }
}
