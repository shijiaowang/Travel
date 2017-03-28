package com.yunspeak.travel.ui.me.mytheme.model;


import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.ui.me.mytheme.PublishAdapter;

import java.util.List;

/**
 * Created by wangyang on 2017/3/21.
 */

public class PublishRecycleModel extends BasePullAndRefreshModel<PublishModel> {
    @Override
    protected RecyclerView.ItemDecoration initChildSpace() {
        return new MyCollectionDecoration(3,6);
    }

    @Override
    protected BaseRecycleViewAdapter<PublishModel> initAdapter(List<PublishModel> datas) {
        return new PublishAdapter(datas, BR.publish, R.layout.item_fragment_publish);
    }
    @Override
    public String url() {
        return IRequestUrl.THEME_MY_PUBLISH;
    }

}
