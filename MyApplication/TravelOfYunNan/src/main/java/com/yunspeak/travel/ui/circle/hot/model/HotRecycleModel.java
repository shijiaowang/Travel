package com.yunspeak.travel.ui.circle.hot.model;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import java.util.List;


/**
 * Created by wangyang on 2017/3/16.
 * 加载刷新管理
 */

public class HotRecycleModel extends BasePullAndRefreshModel<HotPostModel> {


    @BindingAdapter("bind:setRecycleView")
    public static void setRecycleView(RecyclerView recyclerView, List<HotPostModel> datas)  {
        recyclerView.setAdapter(new CommonRecycleViewAdapter<>(datas, BR.hotPostModel, R.layout.item_fragment_circle_hot_post));
    }
    @Override
    public String url() {
        return IRequestUrl.HOT_POST;
    }


}
