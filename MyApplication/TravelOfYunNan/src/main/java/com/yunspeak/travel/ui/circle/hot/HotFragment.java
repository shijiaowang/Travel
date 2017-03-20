package com.yunspeak.travel.ui.circle.hot;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentHotBinding;
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
    private FragmentHotBinding fragmentHotBinding;



    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentHotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_post, container, false);
        return fragmentHotBinding.getRoot();
    }


    @Override
    protected void onReceive(Hot datas) {
        hotRecycleModel.setDatas(datas.getData());
        fragmentHotBinding.setHot(hotRecycleModel);
    }

    @Override
    protected BasePullAndRefreshModel<HotPostModel> initModel() {
        return new HotRecycleModel();
    }
}
