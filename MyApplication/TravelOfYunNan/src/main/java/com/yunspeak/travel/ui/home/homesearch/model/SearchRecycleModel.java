package com.yunspeak.travel.ui.home.homesearch.model;

import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.home.homesearch.HomeSearchActivity;
import com.yunspeak.travel.ui.home.homesearch.SearchAdapter;
import com.yunspeak.travel.ui.me.fans.LineDecoration;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.UIUtils;
import com.yunspeak.travel.BR;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/31.
 */

public class SearchRecycleModel extends BasePullAndRefreshModel<SearchModel> {
    private String type;

    public SearchRecycleModel(String type){

        this.type = type;
    }
    @Override
    protected RecyclerView.ItemDecoration initChildSpace() {
        isRefreshEnable.set(false);//禁止下拉刷新
        return new LineDecoration(UIUtils.getDimen(R.dimen.x70),0);
    }

    @Override
    protected BaseRecycleViewAdapter<SearchModel> initAdapter(List<SearchModel> datas) {
        return new SearchAdapter(datas,BR.searchModel,R.layout.item_activity_home_search_1,type);
    }
    @Override
    protected Map<String, String> initChildParams(MapUtils.Builder builder) {
        return builder.addType(type).addContent(HomeSearchActivity.content).end();
    }


    @Override
    protected String url() {
        return IRequestUrl.HOME_SEARCH;
    }
}
