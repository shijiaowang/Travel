package com.example.administrator.travel.ui.me.mytheme;

import android.support.v4.app.Fragment;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的发表
 */
public class MyPublishFragment extends LoadAndPullBaseFragment<MyPublishEvent,MyPublishBean,MyPublishBean.DataBean> {

    @BindView(R.id.lv_theme) XListView mLvTheme;
    @Override
    protected int initResLayout() {
        return R.layout.fragment_my_theme;
    }
    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return MyPublishEvent.class;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<MyPublishBean.DataBean> httpData) {
        return new ThemeCommonAdapter(getContext(),httpData);
    }

    @Override
    public XListView setXListView() {
        return mLvTheme;
    }
    @Override
    protected String initUrl() {
        return IVariable.THEME_MY_PUBLISH;
    }
}
