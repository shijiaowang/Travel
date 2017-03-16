package com.yunspeak.travel.ui.circle.hot.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.utils.AiteUtils;

import java.util.List;


/**
 * Created by wangyang on 2017/3/16.
 * 加载刷新管理
 */

public class HotRecycleModel extends BasePullAndRefreshModel<HotPostModel> {


    @Override
    public String url() {
        return IRequestUrl.HOT_POST;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @BindingAdapter("bind:setAdapter")
    public static void bindAdapter(RecyclerView recyclerView,List<HotPostModel> list){
        CommonRecycleViewAdapter commonRecycleViewAdapter=new CommonRecycleViewAdapter<>(list, BR.hotPostModel, R.layout.item_fragment_circle_hot_post);
        recyclerView.setAdapter(commonRecycleViewAdapter);
    }

}
