package com.yunspeak.travel.ui.home.welcome.home;

import com.yunspeak.travel.R;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wangyang on 2017/3/9.
 * 主页
 */

public class HomeFragment extends SaveBaseFragment<Home> {

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_home;
    }



    @Override
    protected Map<String, String> initParams() {
        return new HashMap<>();
    }

    @Override
    protected String initUrl() {
        return IRequestUrl.HOME_PAGE;
    }
}
