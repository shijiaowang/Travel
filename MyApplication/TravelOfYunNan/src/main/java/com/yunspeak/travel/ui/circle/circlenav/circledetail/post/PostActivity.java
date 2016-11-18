package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost.ReplyEvent;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
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
    public  String imgLists;
    private int floor=-1;//楼层进入
    private boolean isByFloor=false;//是否通过楼层进入
    private int slideFloor;
    private int currentLoading=TYPE_REFRESH;
    private LinearLayoutManager linearLayoutManager;
    private int loadMore=0;//插入时加载更多计算
    private MenuItem collectionMenu;
    @Override
    protected void initEvent() {
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
        floor = getIntent().getIntExtra(IVariable.FLOOR,-1);
        isByFloor=floor!=-1;
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
    public static void start(Context context, String forum_id,int floor) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(IVariable.FORUM_ID, forum_id);
        intent.putExtra(IVariable.FLOOR, floor);
        context.startActivity(intent);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        if (floor<=0){
            isByFloor=false;//转换为正常浏览模式
        }
        int count;
        if (isByFloor && type==TYPE_REFRESH){//如果是从楼层进入,上拉加载
            if (floor<IVariable.pageCount){
                count=0;
                loadMore=count;
                currentLoading=TYPE_REFRESH;
                slideFloor =floor;//需要滑动到的楼层
            }else if (getListSize(mDatas)==0){
                int size =floor/IVariable.pageCount;
                count=size*IVariable.pageCount;
                slideFloor=floor-count;
                loadMore=count;
                currentLoading=TYPE_REFRESH;
            }else {
                count=floor-IVariable.pageCount;
                currentLoading=count<0?TYPE_REFRESH:TYPE_UP_LOAD;
            }

        }else {
            count = type == TYPE_REFRESH ? 0 : getListSize(mDatas);
            if (count > 0 && type == TYPE_LOAD && !isByFloor) {
                count--;
            }

        }
        if (isByFloor && type==TYPE_LOAD){
            count=loadMore;
        }
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
                collectionMenu.setTitle("已收藏");
                break;
            case TYPE_CANCEL_COLLECTION:
                ToastUtils.showToast("取消收藏成功");
                isCollect=isFalse;
                collectionMenu.setTitle("收藏");
                break;
            case TYPE_LIKE:
                CircleClickBean object = GsonUtils.getObject(postEvent.getResult(), CircleClickBean.class);
                ((PostDetailBean.DataBean.ForumReplyBean) mDatas.get(postEvent.getPosition())).setIs_like("1");
                ((PostDetailBean.DataBean.ForumReplyBean) mDatas.get(postEvent.getPosition())).setLike_count(object.getData().getCount_like());
                mAdapter.notifyItemChanged(postEvent.getPosition());
                break;
            case TYPE_OTHER:
                if (StringUtils.isEmpty(imgLists))return;
                String[] split = imgLists.split(",");
                List<String> images = Arrays.asList(split);
                int position = images.indexOf(postEvent.getUrl());
                if (position==-1)position=0;
                CirclePreviewActivity.start(this,images,position);
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
                if (collectionMenu!=null)collectionMenu.setTitle(isCollect.equals(isTrue)?"已收藏":"收藏");
                userId = forum.getUser_id();
                cId = forum.getCid();
                if ((floor<=IVariable.pageCount)||!isByFloor) {
                    loadDatas.add(forum);//从中间进入的只有在最顶部楼层数小于pagesize 的时候才加载帖子头部
                }
            case TYPE_UP_LOAD:
                if (isByFloor && floor==IVariable.pageCount){//最后一次上拉加载添加头部
                    PostDetailBean.DataBean.ForumBean forum1 = parentBean.getData().getForum();
                    loadDatas.add(forum1);
                    ToastUtils.showToast("加载到顶部啦");
                    isByFloor=false;
                }
            case TYPE_LOAD:
                List<PostDetailBean.DataBean.ForumReplyBean> forum_reply = parentBean.getData().getForum_reply();
                loadDatas.addAll(forum_reply);
                imgLists = parentBean.getData().getImg_lists();
                break;
        }
            normalDeal(postEvent, loadDatas);

    }

    private void normalDeal(PostEvent postEvent, List<Object> loadDatas) {
        if (mAdapter == null) {
            mDatas = loadDatas;
            mAdapter = new PostAdapter(mDatas, this, cId);
            swipeTarget.setAdapter(mAdapter);
            loadMore+=loadDatas.size();
            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setAutoMeasureEnabled(true);
            swipeTarget.setLayoutManager(linearLayoutManager);
            swipeTarget.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (isByFloor){
                        floor=(floor/IVariable.pageCount )*IVariable.pageCount;
                        linearLayoutManager.scrollToPositionWithOffset(slideFloor,0);
                    }
                    swipeTarget.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });

        } else if (postEvent.getType() == TYPE_LOAD) {
            if (isByFloor){
                loadMore+=loadDatas.size();//计算加载更多索引
            }
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
        }else if (postEvent.getType()==TYPE_UP_LOAD){
            //上拉加载
            mDatas.addAll(0,loadDatas);
            mAdapter.notifyDataSetChanged();
            linearLayoutManager.scrollToPositionWithOffset(IVariable.pageCount-1,0);
            swipeContainer.setRefreshing(false);
            floor-= IVariable.pageCount;

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
        return "更多";
    }
    @Subscribe
    public void onEvent(ReplyEvent replyEvent){
        if (!(getListSize(mDatas)%IVariable.pageCount==0) || getListSize(mDatas)==0){//不为0.说明已经加载到了底部，发表帖子后需要加载更多
            onLoad(TYPE_LOAD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_menu,menu);
        collectionMenu = menu.findItem(R.id.action_collection);
        collectionMenu.setTitle(isCollect.equals(isTrue)?"已收藏":"收藏");
        return true;
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (StringUtils.isEmpty(isCollect))return;
        switch (item.getItemId()){
            case R.id.action_collection:
                String url=isCollect.equals(isTrue)?IVariable.CANCEL_COMMON_COLLECTION:IVariable.COLLECTION;
                int type=isCollect.equals(isTrue)?TYPE_CANCEL_COLLECTION:TYPE_COLLECTION;
                Map<String, String> collectionMap = MapUtils.Build().addKey().addUserId().addType("5").addId(forum_id).end();
                XEventUtils.postUseCommonBackJson(url, collectionMap, type, new PostEvent());
                break;
            case R.id.action_report:
                EnterAppointDialog.showDialogAddComplaint(this,forum_id,"1","1","0");
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_discuss:
                CreatePostActivity.start(this, cId, 1, CreatePostActivity.REPLY_POST, forum_id, userId, "0","楼主");
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

    @Override
    protected int changeType(int type) {
        if (type==TYPE_REFRESH) return isByFloor?currentLoading:type;
        return type;
    }
}
