package com.yunspeak.travel.ui.me.mytheme;

import android.support.v4.app.Fragment;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的发表
 */
public class MyPublishFragment extends LoadAndPullBaseFragment<MyPublishEvent,MyPublishBean,MyPublishBean.DataBean> {


    @Override
    protected TravelBaseAdapter initAdapter(List<MyPublishBean.DataBean> httpData) {
        return new ThemeCommonAdapter(getContext(),httpData);
    }

    @Override
    protected String initUrl() {
        return IVariable.THEME_MY_PUBLISH;
    }
}