package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.travel.R;
import com.example.administrator.travel.event.PostEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * create by wangyang
 * 帖子
 */

public class PostActivity extends BaseNetWorkActivity<PostEvent> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener, OnRefreshListener
{

    @BindView(R.id.swipe_target) RecyclerView swipeTarget;
    @BindView(R.id.swipe_container) SwipeToLoadLayout swipeContainer;
    @BindView(R.id.bt_discuss) FloatingActionButton btDiscuss;
    private String forum_id;
    PostAdapter mAdapter;
    private List<Object> mDatas;
    private String cId;
    private String userId;

    @Override
    protected void initEvent() {
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
        cId = getIntent().getStringExtra(IVariable.C_ID);
        btDiscuss.setOnClickListener(this);
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeContainer, false);
        View headerView = inflater.inflate(R.layout.layout_google_header, swipeContainer, false);
        swipeContainer.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        swipeContainer.setLoadMoreFooterView(footView);
        swipeContainer.setRefreshHeaderView(headerView);
        swipeContainer.setOnLoadMoreListener(this);
        swipeContainer.setOnRefreshListener(this);


    }

    public static void start(Context context, String forum_id,String cId) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(IVariable.FORUM_ID, forum_id);
        intent.putExtra(IVariable.C_ID, cId);
        context.startActivity(intent);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count = type==TYPE_REFRESH?0:getListSize(mDatas);
        builder.addFroumId(forum_id).addPageSize().addCount(count);
    }

    @Override
    protected String initUrl() {
        return IVariable.POST_DETAIL;
    }

    @Override
    protected void onSuccess(PostEvent postEvent) {
        PostDetailBean parentBean =  GsonUtils.getObject(postEvent.getResult(),PostDetailBean.class);
        List<Object> loadDatas=new ArrayList<>();
        List<PostDetailBean.DataBean.ForumReplyBean> forum_reply = parentBean.getData().getForum_reply();
        switch (postEvent.getType()){
            case TYPE_REFRESH:
                PostDetailBean.DataBean.ForumBean forum = parentBean.getData().getForum();
                userId = forum.getUser_id();
                loadDatas.add(forum);
                loadDatas.addAll(forum_reply);
                break;
            case TYPE_LOAD:
                loadDatas.addAll(forum_reply);
                break;
        }
        if (mAdapter == null) {
            mDatas = loadDatas;
            mAdapter = new PostAdapter(mDatas,this);
            swipeTarget.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setAutoMeasureEnabled(true);
            swipeTarget.setLayoutManager(linearLayoutManager);
        } else if (postEvent.getType() == TYPE_LOAD) {
            mDatas.addAll(loadDatas);
            if (postEvent.getCode()==2){
                ToastUtils.showToast("没有更多消息了");
            }
            swipeContainer.setLoadingMore(false);
            mAdapter.notifiyData(mDatas);
        } else if (postEvent.getType() == TYPE_REFRESH) {
            mDatas = loadDatas;
            mAdapter.notifiyData(mDatas);
            swipeContainer.setRefreshing(false);
        } else {
            doOtherSuccessData(postEvent);
        }
    }

    /**
     * 处理其他点击事件之类的
     * @param postEvent
     */
    private void doOtherSuccessData(PostEvent postEvent) {

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_discuss:
                CreatePostActivity.start(this,cId,12,CreatePostActivity.REPLY_POST,forum_id,userId,"0");
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
