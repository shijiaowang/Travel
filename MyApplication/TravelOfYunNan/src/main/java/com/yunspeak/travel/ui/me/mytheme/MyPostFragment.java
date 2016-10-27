package com.yunspeak.travel.ui.me.mytheme;

import android.support.v4.app.Fragment;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.ui.view.refreshview.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的帖子
 */
public class MyPostFragment extends LoadAndPullBaseFragment<MyPostEvent,MyPostBean,Object> {

    @Override
    protected BaseRecycleViewAdapter<Object> initAdapter(List<Object> httpData) {
        return new ThemeCommonAdapter(httpData,getContext());
    }

    @Override
    protected void initListener() {
        super.initListener();
        changeMargin(3,6);
    }

    @Override
    protected String initUrl() {
        return IVariable.THEME_MY_POST;
    }
}
