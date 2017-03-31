package com.yunspeak.travel.ui.home.homesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.baseui.OnRecyclerViewScrollListener;
import com.yunspeak.travel.ui.home.homesearch.model.Search;
import com.yunspeak.travel.ui.home.homesearch.model.SearchModel;
import com.yunspeak.travel.ui.home.homesearch.model.SearchRecycleModel;

/**
 * Created by wangyang on 2017/3/31.
 * 搜索结果公共的类
 */

public class SearchFragment extends BaseLoadAndRefreshFragment<Search,SearchModel> {
    private String type;
    private SearchRecycleModel searchRecycleModel;

    @Override
    protected BasePullAndRefreshModel<SearchModel> initModel() {
        return searchRecycleModel;
    }

    @Override
    protected void onReceive(Search datas) {
        super.onReceive(datas);
        if (recyclerView!=null){
            recyclerView.addOnScrollListener(new OnRecyclerViewScrollListener() {
                @Override
                public void onBottom() {
                    //加载更多
                    searchRecycleModel.onLoadMoreListener().onLoadMore();
                }
            });
        }
    }
   //刷新加载数据
   public void  onSearch(){
       statusView.resetSuccess();//重新初始化，以便可以显示刷新加载等等
       isRefresh=true;//设置flag
       childLoad();
   }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(IVariable.TYPE, HomeSearchActivity.SEARCH_USER);
        searchRecycleModel = new SearchRecycleModel(type);
    }


    public static SearchFragment newInstance(String type) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.TYPE, type);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

}
