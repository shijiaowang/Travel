package com.yunspeak.travel.ui.me.mytheme;

import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.me.mytheme.model.Publish;
import com.yunspeak.travel.ui.me.mytheme.model.PublishModel;
import com.yunspeak.travel.ui.me.mytheme.model.PublishRecycleModel;

/**
 * Created by wangyang on 2017/3/21.
 * 我的发表
 */

public class PublishFragment extends BaseLoadAndRefreshFragment<Publish,PublishModel> {

    @Override
    protected BasePullAndRefreshModel<PublishModel> initModel() {
        return new PublishRecycleModel();
    }


}
