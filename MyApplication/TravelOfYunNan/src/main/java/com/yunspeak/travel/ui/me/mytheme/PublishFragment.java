package com.yunspeak.travel.ui.me.mytheme;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentPublishBinding;
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

    private FragmentPublishBinding fragmentPublishBinding;
    PublishRecycleModel publishRecycleModel=new PublishRecycleModel();

    @Override
    protected void onReceive(Publish datas) {
        publishRecycleModel.setDatas(datas.getData());
        fragmentPublishBinding.setPublish(publishRecycleModel);
    }

    @Override
    protected BasePullAndRefreshModel<PublishModel> initModel() {
        return publishRecycleModel;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentPublishBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_publish,container,false);
        return fragmentPublishBinding.getRoot();
    }
}
