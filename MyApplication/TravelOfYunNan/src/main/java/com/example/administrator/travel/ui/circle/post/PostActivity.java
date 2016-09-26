package com.example.administrator.travel.ui.circle.post;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.PostEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 帖子
 */

public class PostActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.lv_post_detail)
    private XListView mLvPostDetail;


    private String forum_id;
    private int currentPage = 0;
    List<Object> postDatas = new ArrayList<>();
    private PostAdapter postAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_post;
    }

    @Override
    protected void initEvent() {
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
    }

    @Override
    protected void onLoad(int typeRefresh) {
        if (forum_id == null) return;
        requestData(TYPE_LOAD);
    }

    private void requestData(int type) {
        Map<String, String> postMap = MapUtils.Build().addKey(this).addFroumId(forum_id).addUserId().addPageSize(10).add(IVariable.PAGE, currentPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.POST_DETAIL, postMap, type,new PostEvent());
    }

    @Override
    protected Activity initViewData() {
        mLvPostDetail.setPullRefreshEnable(true);
        mLvPostDetail.setPullLoadEnable(true);
        mLvPostDetail.setXListViewListener(this);
        mLvPostDetail.setRefreshTime(getTime());

        return this;
    }

    @Override
    protected String setTitleName() {
        return "帖子详情";
    }


    @Override
    public float getAlpha() {
        return 1;
    }

    @Subscribe
    public void onEvent(PostEvent event) {
        if (event.isSuccess()) {
            dealData(event);
        } else {
            ToastUtils.showToast(event.getMessage());
            if (event.getType() == TYPE_LOAD && event.getCode() == 0 && currentPage > 0)
                currentPage--;
        }
        setIsProgress(false);
        loadEnd(mLvPostDetail);//关闭加载或者刷新

    }

    private void dealData(HttpEvent event) {

        dealPost(event);
    }

    private void dealPost(HttpEvent event) {
        PostDetail object = GsonUtils.getObject(event.getResult(), PostDetail.class);
        PostDetail.DataBean.ForumBean forum = object.getData().getForum();
        List<PostDetail.DataBean.ForumReplyBean> forumReply = object.getData().getForum_reply();
        if (postAdapter == null) {
            firstLoad(forum, forumReply);
        } else if (event.getType() == TYPE_REFRESH) {
            List<Object> tempList = new ArrayList<>();
            tempList.add(forum);
            tempList.addAll(forumReply);
            postDatas = tempList;//避免先清空后造成闪屏
            postAdapter.notifyData(postDatas);
        } else {
            afterLoad(forum, forumReply);
        }


    }

    /**
     * 之后加载数据
     *
     * @param forum
     * @param forumReply
     */
    private void afterLoad(PostDetail.DataBean.ForumBean forum, List<PostDetail.DataBean.ForumReplyBean> forumReply) {
        //第一个更换，可能增加点赞等
        if (postDatas.size() > 0) postDatas.remove(0);
        postDatas.add(0, forum);
        postDatas.addAll(forumReply);
        postAdapter.notifyData(postDatas);
    }

    /**
     * 第一次加载数据
     *
     * @param forum
     * @param forumReply
     */
    private void firstLoad(PostDetail.DataBean.ForumBean forum, List<PostDetail.DataBean.ForumReplyBean> forumReply) {
        postDatas.add(forum);
        if (forumReply!=null) {//有时候没有评论
            postDatas.addAll(forumReply);
        }
        postAdapter = new PostAdapter(this, postDatas);
        mLvPostDetail.setAdapter(postAdapter);
    }

    @Override
    public void onRefresh() {
        //刷新
        currentPage = 0;
        requestData(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        requestData(TYPE_LOAD);
    }




}
