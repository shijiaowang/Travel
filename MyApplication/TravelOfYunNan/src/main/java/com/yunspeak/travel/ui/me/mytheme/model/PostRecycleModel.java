package com.yunspeak.travel.ui.me.mytheme.model;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mytheme.PostAdapter;

import java.util.List;

/**
 * Created by wangyang on 2017/3/24.
 */

public class PostRecycleModel extends BasePullAndRefreshModel<PostModel> {
    @Override
    public String url() {
        return IRequestUrl.THEME_MY_POST;
    }
    @BindingAdapter("setPostRecycle")
    public static void setPostRecycle(RecyclerView recycle, List<PostModel> postModels){
        recycle.setAdapter(new PostAdapter(postModels, BR.postModel, R.layout.item_fragment_post));
    }
}
