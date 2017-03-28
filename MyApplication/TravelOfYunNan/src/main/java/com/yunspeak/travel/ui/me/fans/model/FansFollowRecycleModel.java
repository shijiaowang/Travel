package com.yunspeak.travel.ui.me.fans.model;

import android.support.v7.widget.RecyclerView;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.fans.LineDecoration;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/21.
 */

public class FansFollowRecycleModel extends BasePullAndRefreshModel<FansFollowModel> {
    private String type;
    public FansFollowRecycleModel(String type){
        this.type=type;
    }

    @Override
    protected RecyclerView.ItemDecoration initChildSpace() {
        return  new LineDecoration();
    }

    @Override
    protected BaseRecycleViewAdapter<FansFollowModel> initAdapter(List<FansFollowModel> datas) {
        return new CommonRecycleViewAdapter<>(datas, BR.fanFollowModel, R.layout.item_frgment_fans_follow);
    }

    @Override
    protected Map<String, String> initChildParams(MapUtils.Builder builder) {
        return builder.addType(this.type).end();
    }

    @Override
    public String url() {
        return IRequestUrl.GET_FOLLOW_USER;
    }

}
