package com.yunspeak.travel.ui.baseui;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.global.IStatusChange;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyang on 2017/3/16.
 * 统一的下拉刷新 与 上啦加载
 *
 * DataBindingSetRecycleAdapter 为了避免使用静态方法设置adapter
 */

public abstract class BasePullAndRefreshModel<T> extends DataBindingSetRecycleAdapter<T> {
    protected List<T> datas;
    protected  RecyclerView recyclerView;
    protected  CommonRecycleViewAdapter<T> commonRecycleViewAdapter;
    protected  SwipeToLoadLayout swipeToLoadLayout;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public CommonRecycleViewAdapter<T> getCommonRecycleViewAdapter() {
        return commonRecycleViewAdapter;
    }

    public void setCommonRecycleViewAdapter(CommonRecycleViewAdapter<T> commonRecycleViewAdapter) {
        this.commonRecycleViewAdapter = commonRecycleViewAdapter;
    }

    public BasePullAndRefreshModel(){

    }
    public BasePullAndRefreshModel(List<T> datas) {
        this.datas = datas;
    }
    protected  Map<String,String> initChildParams(MapUtils.Builder builder){
        return builder.end();
    }

    public SwipeToLoadLayout getSwipeToLoadLayout() {
        return swipeToLoadLayout;
    }

    public void setSwipeToLoadLayout(SwipeToLoadLayout swipeToLoadLayout) {
        this.swipeToLoadLayout = swipeToLoadLayout;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
    public void refresh(List<T> datas){
        if (commonRecycleViewAdapter!=null) {
            commonRecycleViewAdapter.resetDatas(getDatas());
        }
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setRefreshing(false);
        }
    }
    public void loadMore(List<T> datas){
        if (commonRecycleViewAdapter!=null) {
            commonRecycleViewAdapter.addDatas(datas);
        }
        if (swipeToLoadLayout!=null){
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void setRecycleView(SetRecycleComponent dataBindingComponent, RecyclerView recyclerView,List<T> datas)  {
        this.recyclerView = recyclerView;
        commonRecycleViewAdapter = initAdapter(datas);
        recyclerView.setAdapter(commonRecycleViewAdapter);
        LogUtils.e("connectRecycle" + "关联啦");
    }



    protected abstract CommonRecycleViewAdapter<T> initAdapter(List<T> list);

    public Map<String,String> initParams(){
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId().addPageSize().addCount(datas == null ? 0 : datas.size());
        return initChildParams(builder);
    }
    /**
     * 设置水平的管理器
     * @param recyclerView
     */
    @BindingAdapter("bind:manager")
    public static void bindManager2(RecyclerView recyclerView,int count){
        if (count==0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
        }else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(),count);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

    }
    @BindingAdapter("bind:child_space")
    public static void add(RecyclerView recyclerView,int childSpace){
        recyclerView.addItemDecoration(new MyCollectionDecoration(childSpace,0));
    }

    public abstract String url();


    /**
     * 调用网络请求
     * @param iStatusChange 状态管理，statusview
     * @param eClass   类
     * @param success  自动处理错误成功页面的回调
     * @param callBack   自行处理回调
     * @param <E> 泛型
     * @return Disposable
     */
    public <E extends TravelsObject> Disposable onLoad(IStatusChange iStatusChange, Class<E> eClass, Consumer<E> success, INetworkCallBack<E> callBack){
        HttpClient instance = HttpClient.getInstance();
        Disposable disposable;
        if (callBack==null) {
            disposable=instance.getDataDealErrorAuto(iStatusChange,eClass,success,initParams(),url());
        }else {
            disposable=instance.getDataDealErrorSelf(eClass,callBack,initParams(),url());
        }
        return disposable;
    }
    //自动处理页面逻辑
    public <E extends TravelsObject> Disposable onLoadAuto(IStatusChange iStatusChange, Class<E> eClass,Consumer<E> success){
        return onLoad(iStatusChange,eClass,success,null);
    }
    @Override
    public void init(SetRecycleComponent dataBindingComponent, SwipeToLoadLayout swipeToLoadLayout, int style) {
        this.swipeToLoadLayout = swipeToLoadLayout;
        LayoutInflater inflater = LayoutInflater.from(swipeToLoadLayout.getContext());
        View headerView = inflater.inflate(R.layout.layout_google_header, swipeToLoadLayout, false);
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeToLoadLayout, false);
        swipeToLoadLayout.setSwipeStyle(style);
        swipeToLoadLayout.setRefreshHeaderView(headerView);
        swipeToLoadLayout.setLoadMoreFooterView(footView);
    }


}
