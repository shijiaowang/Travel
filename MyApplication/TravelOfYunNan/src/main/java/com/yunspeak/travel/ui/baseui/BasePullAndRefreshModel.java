package com.yunspeak.travel.ui.baseui;
import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/16.
 * 统一的下拉刷新 与 上啦加载
 */

public abstract class BasePullAndRefreshModel<T>{
    private List<T> datas;
    public BasePullAndRefreshModel(){

    }
    public BasePullAndRefreshModel(List<T> datas) {
        this.datas = datas;
    }
    protected  Map<String,String> initChildParams(MapUtils.Builder builder){
        return builder.end();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }


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
    @BindingAdapter("bind:init")
    public static void initSwipeLayout(SwipeToLoadLayout swipeToLoadLayout,int style){
        LayoutInflater inflater = LayoutInflater.from(swipeToLoadLayout.getContext());
        View headerView = inflater.inflate(R.layout.layout_google_header, swipeToLoadLayout, false);
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeToLoadLayout, false);
        swipeToLoadLayout.setSwipeStyle(style);
        swipeToLoadLayout.setRefreshHeaderView(headerView);
        swipeToLoadLayout.setLoadMoreFooterView(footView);
    }
    public abstract String url();
    public abstract void onRefresh();
    public abstract void onLoadMore();

}
