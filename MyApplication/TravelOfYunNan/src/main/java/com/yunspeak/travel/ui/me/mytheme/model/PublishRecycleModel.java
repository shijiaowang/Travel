package com.yunspeak.travel.ui.me.mytheme.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mytheme.PublishAdapter;

import java.util.List;

/**
 * Created by wangyang on 2017/3/21.
 */

public class PublishRecycleModel extends BasePullAndRefreshModel<PublishModel> {
    @Override
    public String url() {
        return IRequestUrl.THEME_MY_PUBLISH;
    }
    @BindingAdapter("setPublishRecycle")
    public static void setPublucRecycle(RecyclerView recycle, List<PublishModel> publishes){
        recycle.setAdapter(new PublishAdapter(publishes, BR.publish, R.layout.item_fragment_publish));
    }
}
