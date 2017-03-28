package com.yunspeak.travel.ui.me.mytheme;

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
    PostRecycleModel postRecycleModel=new PostRecycleModel();


    @Override
    protected BasePullAndRefreshModel<PostModel> initModel() {
        return postRecycleModel;
    }

}
