package com.yunspeak.travel.ui.home.homesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yunspeak.travel.bean.SearchCommonBean;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by  wangyang on 2016/8/22 0022.
 */
public class SearchCommonFragment extends LoadAndPullBaseFragment<HomeSearchEvent, SearchCommonBean, SearchCommonBean.DataBean> {


    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(IVariable.TYPE, HomeSearchActivity.SEARCH_USER);
    }


    public static SearchCommonFragment newInstance(String type) {
        SearchCommonFragment searchCommonFragment = new SearchCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.TYPE, type);
        searchCommonFragment.setArguments(bundle);
        return searchCommonFragment;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwipe.setRefreshEnabled(false);
        mRvCommon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState==RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if (lastCompletelyVisibleItemPosition==itemCount-1){
                        //滑动到最后了开始加载
                        mSwipe.setLoadingMore(true);
                        onLoadMore();
                    }
                }


            }

        });
    }

    @Override
    protected void onLoad(int t) {
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId().addType(type).addContent(HomeSearchActivity.content);
        childAdd(builder, t);
        Map<String, String> baseMap = builder.end();
        HomeSearchEvent homeSearchEvent = new HomeSearchEvent();
        homeSearchEvent.setSearchType(type);
        XEventUtils.getUseCommonBackJson(initUrl(), baseMap, t, homeSearchEvent);
    }

    @Override
    public void onSuccess(HomeSearchEvent homeSearchEvent) {
        if (homeSearchEvent.getSearchType().equals(type)) {
            switch (homeSearchEvent.getType()) {
                case IState.TYPE_UPDATE:
                    ToastUtils.showToast(homeSearchEvent.getMessage());
                    SearchCommonBean.DataBean dataBean = mDatas.get(homeSearchEvent.getPosition());
                    dataBean.setCname(dataBean.getCname().equals("1") ? "0" : "1");
                    mAdapter.notifyItemChanged(homeSearchEvent.getPosition());
                    break;
                default:
                    super.onSuccess(homeSearchEvent);
                    break;
            }

        }
    }


    public void search() {
        isSuccessed=false;
        onLoad(TYPE_REFRESH);
    }

    @Override
    protected String initUrl() {
        return IVariable.HOME_SEARCH;
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<SearchCommonBean.DataBean> mDatas) {
        return new SearchCommonAdapter(mDatas, getContext(), type);
    }

    @Override
    protected void onFail(HomeSearchEvent homeSearchEvent) {
        super.onFail(homeSearchEvent);
        switch (homeSearchEvent.getType()) {
            case IState.TYPE_UPDATE:
                ToastUtils.showToast(homeSearchEvent.getMessage());
                break;
        }
    }
}
