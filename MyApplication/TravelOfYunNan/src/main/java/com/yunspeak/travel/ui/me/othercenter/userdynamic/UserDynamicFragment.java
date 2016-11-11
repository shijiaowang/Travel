package com.yunspeak.travel.ui.me.othercenter.userdynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.LoadMoreListener;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.yunspeak.travel.ui.me.othercenter.INotify;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterBean;
import com.yunspeak.travel.ui.me.othercenter.useralbum.AppBarStateEvent;
import com.yunspeak.travel.ui.view.LoadingPage;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/13 0013.
 * 用户动态
 */

public class UserDynamicFragment extends LoadBaseFragment<UserDynamicEvent> implements INotify<OtherUserCenterBean.DataBean.MoreBean>, OnLoadMoreListener {
    private boolean isFirst = true;
    private String userId;
    @BindView(R.id.swipe_target)
    RecyclerView mRvRecycle;
    @BindView(R.id.swipe_container)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<OtherUserCenterBean.DataBean.MoreBean> mDataBean = new ArrayList<>();
    private UserDynamicAdapter userDynamicAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString(IVariable.USER_ID);
        mDataBean = (List<OtherUserCenterBean.DataBean.MoreBean>) getArguments().getSerializable(IVariable.DATA);
    }

    @Override
    protected int initResLayout() {
        return R.layout.activity_other_dynamic;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //处理数据
        initData();
        setState(LoadingPage.ResultState.STATE_SUCCESS);
    }

    private void initData() {
        if (mDataBean == null) {
            mDataBean = new ArrayList<>();
        }
        if (userDynamicAdapter == null) {
            userDynamicAdapter = new UserDynamicAdapter(mDataBean, getContext());
            mRvRecycle.setAdapter(userDynamicAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mRvRecycle.setLayoutManager(linearLayoutManager);
            mRvRecycle.addItemDecoration(new MyWitheMeDecoration(6));
        } else {
            userDynamicAdapter.notifiyData(mDataBean);

        }
    }


    public static UserDynamicFragment newInstance(String userId, List<OtherUserCenterBean.DataBean.MoreBean> mDataBean) {
        UserDynamicFragment userDynamicFragment = new UserDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IVariable.USER_ID, userId);
        bundle.putSerializable(IVariable.DATA, (Serializable) mDataBean);
        userDynamicFragment.setArguments(bundle);
        return userDynamicFragment;
    }


    @Override
    public void onSuccess(UserDynamicEvent userDynamicEvent) {
        if (isFirst) return;
        swipeToLoadLayout.setLoadingMore(false);
        OtherUserCenterBean otherUserCenterBean = GsonUtils.getObject(userDynamicEvent.getResult(), OtherUserCenterBean.class);
        mDataBean.addAll(otherUserCenterBean.getData().getMore());
        initData();
    }

    @Override
    protected void onFail(UserDynamicEvent event) {
        swipeToLoadLayout.setLoadingMore(false);

    }

    @Override
    protected void initListener() {
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_google_footer, swipeToLoadLayout, false);
        swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        swipeToLoadLayout.setLoadMoreFooterView(footView);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }
    @Override
    protected void onLoad(int type) {
        if (isFirst) {
            isFirst = false;
        } else {
            super.onLoad(type);

        }
    }

    @Override
    protected String initUrl() {
        return IVariable.OTHER_USER_INFO;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addMyId().add(IVariable.USER_ID, userId).addType("1").addPageSize().addCount(mDataBean.size());

    }


    @Override
    public void notifys(List<OtherUserCenterBean.DataBean.MoreBean> t) {
        mDataBean=t;
        initData();
    }

    @Override
    public void notify(OtherUserCenterBean.DataBean.MoreBean moreBean) {

    }
    @Subscribe
    public void onEvent(AppBarStateEvent appBarStateEvent) {
            swipeToLoadLayout.setLoadMoreEnabled(appBarStateEvent.isClose());

    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }
}
