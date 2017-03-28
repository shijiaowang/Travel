package com.yunspeak.travel.ui.baseui;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableBoolean;
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
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.utils.MapUtils;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import  com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
/**
 * Created by wangyang on 2017/3/16.
 * 统一的下拉刷新 与 上啦加载
 */

public abstract class BasePullAndRefreshModel<T>{
    public final ObservableBoolean isRefreshing=new ObservableBoolean(false);//上啦刷新双向绑定
    public final ObservableBoolean isLoading=new ObservableBoolean(false);//下拉加载双向绑定
    public final ObservableBoolean isLoadingEnable=new ObservableBoolean(true);//是否还有数据加载
    public boolean isAddHeader=false;//上拉是否将数据添加到头部
    private int pageCount;

    public boolean isAddHeader() {
        return isAddHeader;
    }

    public void setAddHeader(boolean addHeader) {
        isAddHeader = addHeader;
    }

    private BaseRecycleViewAdapter<T> baseRecycleViewAdapter;
    private boolean isRefresh=true;

    public BaseRecycleViewAdapter<T> getBaseRecycleViewAdapter() {
        return baseRecycleViewAdapter;
    }
    public  void setRecycleView(RecyclerView recyclerView, List<T> datas){
        this.baseRecycleViewAdapter=initAdapter(datas);
        if (getSize(datas)<IVariable.pageCount){
            isLoadingEnable.set(false);
        }
        recyclerView.setAdapter(baseRecycleViewAdapter);
        recyclerView.addItemDecoration(initChildSpace());

    }

    protected abstract RecyclerView.ItemDecoration initChildSpace();


    protected abstract BaseRecycleViewAdapter<T> initAdapter(List<T> datas);

    public BasePullAndRefreshModel(){

    }

    protected  Map<String,String> initChildParams(MapUtils.Builder builder){
        return builder.end();
    }

    private int getSize(List<T> datas){
        return datas==null?0:datas.size();
    }
    public void refresh(List<T> datas){
        isRefreshing.set(false);
        if (baseRecycleViewAdapter==null)return;
        if (getSize(datas)<pageCount){
            isLoadingEnable.set(false);
        }else if (!isLoadingEnable.get()){
            isLoadingEnable.set(true);
        }
        if (isAddHeader){
            baseRecycleViewAdapter.addHeaderDatas(datas,!isLoadingEnable.get());//添加到头部
        }else {
            baseRecycleViewAdapter.resetDatas(datas,!isLoadingEnable.get());
        }

    }
    public void loadMore(List<T> datas){
        isLoading.set(false);
        if (getSize(datas)<pageCount){
            isLoadingEnable.set(false);
        }else if (!isLoadingEnable.get()){
            isLoadingEnable.set(true);
        }
        if (baseRecycleViewAdapter!=null) {
            baseRecycleViewAdapter.addDatas(datas,!isLoadingEnable.get());
        }
    }


    public Map<String, String> initParams() {
        int count = getCount();
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId().addPageSize(getPageCount()).addCount(count);
        return initChildParams(builder);
    }
    public int getPageCount() {
        pageCount=isRefresh && !isAddHeader ?(baseRecycleViewAdapter == null?IVariable.pageCount:baseRecycleViewAdapter.getDataSize()):IVariable.pageCount;
        pageCount=pageCount<IVariable.pageCount?IVariable.pageCount:pageCount;
        return pageCount;
    }
    /**
     * 计算个数
     * @return
     */
    private int getCount() {
        return isRefresh && !isAddHeader ? 0 : baseRecycleViewAdapter == null?0:baseRecycleViewAdapter.getDataSize();
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

    public abstract String url();
    @BindingAdapter("app:load_more_enabled")
    public static void setEnable(SwipeToLoadLayout swipeToLoadLayout,boolean isTrue){
        swipeToLoadLayout.setLoadMoreEnabled(isTrue);
    }

    /**
     * 调用网络请求
     * @param <E> 泛型
     * @param iStatusChange 状态管理，statusview
     * @param eClass   类
     * @param success  自动处理错误成功页面的回调
     * @param callBack   自行处理回调
     * @param isRefresh
     * @return Disposable
     */
    public <E extends TravelsObject> Disposable onLoad(IStatusChange iStatusChange, Class<E> eClass, Consumer<E> success, INetworkCallBack<E> callBack, boolean isRefresh){
        this.isRefresh = isRefresh;
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
    public <E extends TravelsObject> Disposable onLoadAuto(IStatusChange iStatusChange, Class<E> eClass,Consumer<E> success,boolean isRefresh){
        return onLoad(iStatusChange,eClass,success,null,isRefresh);
    }
    @BindingAdapter("bind:init")
    public static void init(SwipeToLoadLayout swipeToLoadLayout, int style) {
        LayoutInflater inflater = LayoutInflater.from(swipeToLoadLayout.getContext());
        View headerView = inflater.inflate(R.layout.layout_google_header, swipeToLoadLayout, false);
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeToLoadLayout, false);
        swipeToLoadLayout.setSwipeStyle(style);
        swipeToLoadLayout.setRefreshHeaderView(headerView);
        swipeToLoadLayout.setLoadMoreFooterView(footView);
    }
    //以下为设置上啦加载的双向绑定事件
    @BindingAdapter("loading")
    public static void setLoading(SwipeToLoadLayout view, boolean loadingMore) {
        if (loadingMore != view.isLoadingMore()) {
            view.setLoadingMore(loadingMore);
        }
    }
    @InverseBindingAdapter(attribute = "loading", event = "loadingAttrChanged")
    public static boolean isLoading(SwipeToLoadLayout view) {
        return view.isLoadingMore();
    }

    @BindingAdapter(value = {"onLoadingMoreListener", "loadingAttrChanged"}, requireAll = false)
    public static void setOnLoadingMoreListener(final SwipeToLoadLayout view,
                                            final OnLoadMoreListener listener,
                                                final InverseBindingListener loadingAttrChanged) {

        OnLoadMoreListener newValue = new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (listener != null) {
                    listener.onLoadMore();
                }
                if (loadingAttrChanged != null) {
                    loadingAttrChanged.onChange();
                }
            }
        };
        view.setOnLoadMoreListener(newValue);
    }
    public OnLoadMoreListener onLoadMoreListener() {
        return new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载数据
                if (onLoadListener!=null){
                    onLoadListener.onLoad(false);
                }
            }
        };
    }
   //设置刷新的双向绑定事件
    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeToLoadLayout view, boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            view.setRefreshing(refreshing);
        }
    }
    @InverseBindingAdapter(attribute = "refreshing", event = "refreshingAttrChanged")
    public static boolean isRefreshing(SwipeToLoadLayout view) {
        return view.isRefreshing();
    }

    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
    public static void setOnRefreshListener(final SwipeToLoadLayout view,
                                            final OnRefreshListener listener,
                                            final InverseBindingListener refreshingAttrChanged) {
        if (refreshingAttrChanged != null) {
            refreshingAttrChanged.onChange();
        }
        OnRefreshListener newValue = new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onRefresh();
                }
                if (refreshingAttrChanged != null) {
                    refreshingAttrChanged.onChange();
                }
            }
        };
        view.setOnRefreshListener(newValue);
    }
    public OnRefreshListener onRefreshListener() {
        return new OnRefreshListener() {
            @Override
            public void onRefresh() {
              //加载数据
                if (onLoadListener!=null){
                    onLoadListener.onLoad(true);
                }

            }
        };
    }



    public   interface OnLoadListener{
        void onLoad(boolean isRefresh);
    }
    protected OnLoadListener onLoadListener;


    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
