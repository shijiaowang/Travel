package com.yunspeak.travel.ui.home.homesearch;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.home.homesearch.model.SearchModel;

import java.util.List;

/**
 * Created by wangyang on 2017/3/31.
 */

public class SearchAdapter extends CommonRecycleViewAdapter<SearchModel> {
    private String type;

    public SearchAdapter(List<SearchModel> datas, int brId, int layoutId, String type) {
        super(datas, brId, layoutId);
        this.type = type;
    }


    @Override
    protected void onBind(ViewDataBinding binding) {
        binding.setVariable(BR.searchType,type);
    }

}
