package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.PostEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.PostAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 帖子
 */

public class PostActivity extends LoadingBarBaseActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.lv_post_detail)
    private XListView mLvPostDetail;
    public static final int TYPE_REFRESH = 1;
    public static final int LOAD_MORE = 0;

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

    }

    @Override
    protected void onLoad() {
        if (forum_id == null) return;
        requestData(LOAD_MORE);
    }

    private void requestData(int type) {
        Map<String, String> postMap = MapUtils.Build().addKey(this).add(IVariable.FORUM_ID, "24").add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()).add(IVariable.PAGE_SIZE, "4").add(IVariable.PAGE, currentPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.POST_DETAIL, postMap, type,new PostEvent());
    }

    @Override
    protected Activity initViewData() {
        mLvPostDetail.setPullRefreshEnable(true);
        mLvPostDetail.setPullLoadEnable(true);
        mLvPostDetail.setXListViewListener(this);
        mLvPostDetail.setRefreshTime(getTime());
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
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
            if (event.getType() == LOAD_MORE && event.getCode() == 0 && currentPage > 0)
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
        postDatas.addAll(forumReply);
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
        requestData(LOAD_MORE);
    }




}
