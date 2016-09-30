package com.example.administrator.travel.ui.me.mytheme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.me.messagecenter.MeCommonEvent;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的帖子
 */
public class MyPostFragment extends LoadAndPullBaseFragment<MyPostEvent,MyPostBean,MyPostBean.DataBean> {

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
        return MyPostEvent.class;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<MyPostBean.DataBean> httpData) {
        return new ThemeCommonAdapter(getContext(),httpData);
    }


    @Override
    public XListView setXListView() {
        return mLvTheme;
    }
    @Override
    protected String initUrl() {
        return IVariable.THEME_MY_POST;
    }
}
