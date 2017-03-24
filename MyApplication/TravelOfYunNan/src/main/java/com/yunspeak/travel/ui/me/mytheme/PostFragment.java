package com.yunspeak.travel.ui.me.mytheme;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentPostBinding;
import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mytheme.model.Post;
import com.yunspeak.travel.ui.me.mytheme.model.PostModel;
import com.yunspeak.travel.ui.me.mytheme.model.PostRecycleModel;

/**
 * Created by wangyang on 2017/3/24.
 * 我的帖子 主题
 */

public class PostFragment extends BaseLoadAndRefreshFragment<Post,PostModel> {

    private FragmentPostBinding fragmentPublishBinding;
    PostRecycleModel postRecycleModel=new PostRecycleModel();

    @Override
    protected void onReceive(Post datas) {
        postRecycleModel.setDatas(datas.getData());
        fragmentPublishBinding.setPost(postRecycleModel);
    }

    @Override
    protected BasePullAndRefreshModel<PostModel> initModel() {
        return postRecycleModel;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentPublishBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post,container,false);
        return fragmentPublishBinding.getRoot();
    }
}
