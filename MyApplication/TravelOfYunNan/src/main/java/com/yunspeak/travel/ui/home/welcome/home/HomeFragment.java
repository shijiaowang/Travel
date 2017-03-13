package com.yunspeak.travel.ui.home.welcome.home;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.HomeFragmentBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.SystemBarHelper;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;
import com.yunspeak.travel.ui.home.welcome.home.model.Home;
import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;


/**
 * Created by wangyang on 2017/3/9.
 * 主页
 */

public class HomeFragment extends SaveBaseFragment<Home> {


    private HomeFragmentBinding viewDataBinding;



    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_home, container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    protected void initOptions() {
        super.initOptions();
        viewDataBinding.setStatusBarHeight(SystemBarHelper.getStatusBarHeight(getContext()));
    }

    @Override
    protected void receiveData(Home data) {
        Home.DataBean homeData = data.getData();
        viewDataBinding.setActivityModel(homeData.getActivit());
        viewDataBinding.setIndexTogether(homeData.getIndex_text().get(0));
        viewDataBinding.setIndexOldDriver(homeData.getIndex_text().get(1));
        viewDataBinding.setIndexDiyDriver(homeData.getIndex_text().get(2));
    }

    @Override
    protected Map<String, String> initParams() {
        return MapUtils.Build().addKey().end();
    }

    @Override
    protected String initUrl() {
        return IRequestUrl.HOME_PAGE;
    }
}
