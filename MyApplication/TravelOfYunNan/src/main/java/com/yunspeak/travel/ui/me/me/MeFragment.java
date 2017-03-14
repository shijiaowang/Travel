package com.yunspeak.travel.ui.me.me;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.ucrop.UCrop;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentMeBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.CropBaseFragment;
import com.yunspeak.travel.ui.me.me.model.Me;
import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/14.
 * 我的 个人中心
 */

public class MeFragment extends CropBaseFragment<Me> {

    private FragmentMeBinding fragmentMeBinding;

    @Override
    protected void receiveData(Me data) {
         fragmentMeBinding.setMeData(data.getData());
    }

    @Override
    protected Map<String, String> initParams() {
        return MapUtils.Build().addKey().addUserId().end();
    }

    @Override
    protected String initUrl() {
        return IRequestUrl.ME_MESSAGE;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentMeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_me, container, false);
        return fragmentMeBinding.getRoot();
    }

    @Override
    protected void setOptions(UCrop.Options options) {

    }

    @Override
    protected void childUpImage() {

    }

    @Override
    protected void childViewShow(String s) {

    }
}
