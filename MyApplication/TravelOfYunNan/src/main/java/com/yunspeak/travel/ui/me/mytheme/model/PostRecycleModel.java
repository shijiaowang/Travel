package com.yunspeak.travel.ui.me.mytheme.model;

import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.adapter.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mycollection.collectiondetail.MyCollectionDecoration;
import com.yunspeak.travel.ui.me.mytheme.PostAdapter;

import java.util.List;

/**
 * Created by wangyang on 2017/3/24.
 */

public class PostRecycleModel extends BasePullAndRefreshModel<PostModel> {
    @Override
    protected RecyclerView.ItemDecoration initChildSpace() {
        return new MyCollectionDecoration(3,6);
    }

    @Override
    protected BaseRecycleViewAdapter<PostModel> initAdapter(List<PostModel> datas) {
        return new PostAdapter(datas, BR.postModel, R.layout.item_fragment_post);
    }

    @Override
    public String url() {
        return IRequestUrl.THEME_MY_POST;
    }

}
