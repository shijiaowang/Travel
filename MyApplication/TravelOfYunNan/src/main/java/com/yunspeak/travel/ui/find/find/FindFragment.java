package com.yunspeak.travel.ui.find.find;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentFindBinding;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.ui.baseui.SystemBarHelper;
import com.yunspeak.travel.ui.find.find.model.Find;
import com.yunspeak.travel.ui.find.find.model.OtherControlModel;
import com.yunspeak.travel.ui.fragment.SaveBaseFragment;
import com.yunspeak.travel.ui.view.PagerCursorView;
import com.yunspeak.travel.utils.MapUtils;

import java.util.Map;


/**
 * Created by wangyang on 2017/3/13.
 * 首页 发现
 */

public class FindFragment extends SaveBaseFragment<Find> implements SwipeRefreshLayout.OnRefreshListener {

    ViewPager viewPager;
    PagerCursorView pagerCursorView;

    private FragmentFindBinding fragmentFindBinding;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentFindBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_find, container, false);
        pagerCursorView= (PagerCursorView) fragmentFindBinding.getRoot().findViewById(R.id.pager_cursor);
        viewPager= (ViewPager) fragmentFindBinding.getRoot().findViewById(R.id.vp_find);
        swipeRefreshLayout = (SwipeRefreshLayout) fragmentFindBinding.getRoot().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.otherTitleBg));
        swipeRefreshLayout.setOnRefreshListener(this);
        return fragmentFindBinding.getRoot();
    }
    @Override
    protected void receiveData(Find data) {
        swipeRefreshLayout.setRefreshing(false);
        Find.DataBean findData = data.getData();
        pagerCursorView.setViewPager(viewPager,findData.getBanner().size(),true,this);
        fragmentFindBinding.setFindData(findData);
    }

    @Override
    protected void initOptions() {
        super.initOptions();
         fragmentFindBinding.setOtherControl(new OtherControlModel());
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

    @Override
    protected boolean onError(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        return super.onError(throwable);
    }

    @Override
    public void onRefresh() {
        childLoad();
    }
}
