package com.yunspeak.travel.ui.home.homesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
