package com.yunspeak.travel.ui.find.find;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentFindBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.SystemBarHelper;
import com.yunspeak.travel.ui.find.find.model.Find;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;
import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;

/**
 * Created by wangyang on 2017/3/13.
 * 首页 发现
 */

public class FindFragment extends SaveBaseFragment<Find> {

    private FragmentFindBinding fragmentFindBinding;
    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentFindBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_find, container, false);
        return fragmentFindBinding.getRoot();
    }
    @Override
    protected void receiveData(Find data) {

    }

    @Override
    protected void initOptions() {
        super.initOptions();
         fragmentFindBinding.setStatusBarHeight(SystemBarHelper.getStatusBarHeight(getContext()));
    }

    @Override
    protected Map<String, String> initParams() {
        return MapUtils.Build().addKey().end();
    }

    @Override
    protected String initUrl() {
        return IRequestUrl.FIND_HOME;
    }


}
