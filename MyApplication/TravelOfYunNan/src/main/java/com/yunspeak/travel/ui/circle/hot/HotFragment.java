package com.yunspeak.travel.ui.circle.hot;


import com.yunspeak.travel.ui.baseui.BaseLoadAndRefreshFragment;
import com.yunspeak.travel.ui.baseui.BasePullAndRefreshModel;
import com.yunspeak.travel.ui.circle.hot.model.Hot;
import com.yunspeak.travel.ui.circle.hot.model.HotPostModel;
import com.yunspeak.travel.ui.circle.hot.model.HotRecycleModel;



/**
 * Created by wangyang on 2017/3/16.
 * 圈子 热帖页面
 */

public class HotFragment extends BaseLoadAndRefreshFragment<Hot,HotPostModel> {
    HotRecycleModel hotRecycleModel=new HotRecycleModel();

    @Override
    protected BasePullAndRefreshModel<HotPostModel> initModel() {
        return hotRecycleModel;
    }
}
