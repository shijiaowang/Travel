package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.travel.R;
import com.example.administrator.travel.event.PostEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointDetailEvent;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * create by wangyang
 * 帖子
 */

public class PostActivity extends BaseNetWorkActivity<PostEvent> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_container)
    SwipeToLoadLayout swipeContainer;
    @BindView(R.id.bt_discuss)
    FloatingActionButton btDiscuss;
    private String forum_id;
    PostAdapter mAdapter;
    private List<Object> mDatas;
    private String cId;
    private String userId;
    private String isCollect=isFalse;

    @Override
    protected void initEvent() {
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
        btDiscuss.setOnClickListener(this);
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeContainer, false);
        View headerView = inflater.inflate(R.layout.layout_google_header, swipeContainer, false);
        swipeContainer.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        swipeContainer.setLoadMoreFooterView(footView);
        swipeContainer.setRefreshHeaderView(headerView);
        swipeContainer.setOnLoadMoreListener(this);
        swipeContainer.setOnRefreshListener(this);


    }

    public static void start(Context context, String forum_id) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(IVariable.FORUM_ID, forum_id);
        context.startActivity(intent);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
        builder.addFroumId(forum_id).addPageSize().addCount(count);
    }

    @Override
    protected String initUrl() {
        return IVariable.POST_DETAIL;
    }

    @Override
    protected void onSuccess(PostEvent postEvent) {
        switch (postEvent.getType()){
            case TYPE_COLLECTION:
                ToastUtils.showToast("收藏成功");
                isCollect=isTrue;
                item.setTitle("已收藏");
                break;
            case TYPE_CANCEL_COLLECTION:
                ToastUtils.showToast("取消收藏成功");
                isCollect=isFalse;
                item.setTitle("收藏");
                break;
            default:
                dealLoad(postEvent);
                break;
        }

    }

    /**
     * 处理 数据加载逻辑
     * @param postEvent
     */
    private void dealLoad(PostEvent postEvent) {
        PostDetailBean parentBean = GsonUtils.getObject(postEvent.getResult(), PostDetailBean.class);
        List<Object> loadDatas = new ArrayList<>();
        switch (postEvent.getType()) {
            case TYPE_REFRESH:
                PostDetailBean.DataBean.ForumBean forum = parentBean.getData().getForum();
                isCollect = forum.getIs_collect();
                if (item!=null)item.setTitle(isCollect.equals(isTrue)?"已收藏":"收藏");
                userId = forum.getUser_id();
                cId = forum.getCid();
                loadDatas.add(forum);
            case TYPE_LOAD:
                List<PostDetailBean.DataBean.ForumReplyBean> forum_reply = parentBean.getData().getForum_reply();
                loadDatas.addAll(forum_reply);
                break;
        }
        if (mAdapter == null) {
            mDatas = loadDatas;
            mAdapter = new PostAdapter(mDatas, this, cId);
            swipeTarget.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setAutoMeasureEnabled(true);
            swipeTarget.setLayoutManager(linearLayoutManager);
        } else if (postEvent.getType() == TYPE_LOAD) {
            mDatas.addAll(loadDatas);
            swipeContainer.setLoadingMore(false);
            if (postEvent.getCode() == 2) {
                ToastUtils.showToast("没有更多消息了");
            } else {
                mAdapter.notifiyData(mDatas);
            }
        } else if (postEvent.getType() == TYPE_REFRESH) {
            mDatas = loadDatas;
            mAdapter.notifiyData(mDatas);
            swipeContainer.setRefreshing(false);
        }
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_post_content;
    }

    @Override
    protected String initTitle() {
        return "帖子详情";
    }

    @Override
    protected String initRightText() {
        return isCollect.equals(isTrue)?"已收藏":"收藏";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        String url=isCollect.equals(isTrue)?IVariable.CANCEL_COLLECTION:IVariable.COLLECTION;
        Map<String, String> collectionMap = MapUtils.Build().addKey(this).addUserId().addType("5").addId(forum_id).end();
        XEventUtils.postUseCommonBackJson(url, collectionMap, TYPE_COLLECTION, new PostEvent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_discuss:
                CreatePostActivity.start(this, cId, 12, CreatePostActivity.REPLY_POST, forum_id, userId, "0");
                break;
        }
    }


    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }

    @Override
    protected void onFail(PostEvent t) {
        super.onFail(t);
        switch (t.getType()) {
            case TYPE_REFRESH:
                swipeContainer.setRefreshing(false);
                break;
            case TYPE_LOAD:
                swipeContainer.setLoadingMore(false);
                break;
        }
    }


}
