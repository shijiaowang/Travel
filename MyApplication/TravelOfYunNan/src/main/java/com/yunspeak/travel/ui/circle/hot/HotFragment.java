package com.yunspeak.travel.ui.circle.hot;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentHotBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.circle.hot.model.Hot;
import com.yunspeak.travel.ui.circle.hot.model.HotRecycleModel;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/16.
 * 圈子 热帖页面
 */

public class HotFragment extends SaveBaseFragment<Hot> {
    HotRecycleModel hotRecycleModel=new HotRecycleModel();
    private FragmentHotBinding fragmentHotBinding;

    @Override
    protected void receiveData(Hot data) {
        hotRecycleModel.setDatas(data.getData());
        fragmentHotBinding.setHot(hotRecycleModel);
    }

    @Override
    protected Map<String, String> initParams() {
        return hotRecycleModel.initParams();
    }

    @Override
    protected String initUrl() {
        return hotRecycleModel.url();
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentHotBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_post, container, false);
        return fragmentHotBinding.getRoot();
    }
}
