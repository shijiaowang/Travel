package com.yunspeak.travel.ui.home.welcome.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;
import com.yunspeak.travel.ui.home.welcome.home.model.ActivityBean;
import com.yunspeak.travel.ui.home.welcome.home.model.Home;
import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;


/**
 * Created by wangyang on 2017/3/9.
 * 主页
 */

public class HomeFragment extends SaveBaseFragment<Home> {


    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_home_home;
    }



    @Override
    protected void initOptions() {
        super.initOptions();
    }

    @Override
    protected void receiveData(Home data) {

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
