package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.PostAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
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

public class PostActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_post_detail)
    private ListView mLvPostDetail;


    private String forum_id;
    private int currentPage = 0;
    List<Object> postDatas=new ArrayList<>();
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
        Map<String, String> postMap = MapUtils.Build().addKey(this).add(IVariable.FORUM_ID, "24").add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()).add(IVariable.PAGE_SIZE, "4").add(IVariable.PAGE, currentPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.POST_DETAIL,postMap,0);
    }

    @Override
    protected Activity initViewData() {
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
    public void onEvent(HttpEvent event){
        if (event.isSuccess()){
            dealData(event);
        }else {
            ToastUtils.showToast(event.getMessage());
        }

    }

    private void dealData(HttpEvent event) {
        setIsProgress(false);
        if (event.isSuccess()) {
            dealPost(event);
        }else {
            ToastUtils.showToast(event.getMessage());
        }

    }

    private void dealPost(HttpEvent event) {
        PostDetail object = GsonUtils.getObject(event.getResult(), PostDetail.class);
        PostDetail.DataBean.ForumBean forum = object.getData().getForum();
        List<PostDetail.DataBean.ForumReplyBean> forumReply = object.getData().getForum_reply();
        if (postAdapter==null) {
            postDatas.add(forum);
            postDatas.addAll(forumReply);
            postAdapter = new PostAdapter(this, postDatas);
            mLvPostDetail.setAdapter(postAdapter);
        }else {
            //第一个更换，可能增加点赞等
            postDatas.remove(0);
            postDatas.add(0,forum);
            postDatas.addAll(forumReply);
            postAdapter.notifyData(postDatas);
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        unregisterEventBus(this);
    }
}
