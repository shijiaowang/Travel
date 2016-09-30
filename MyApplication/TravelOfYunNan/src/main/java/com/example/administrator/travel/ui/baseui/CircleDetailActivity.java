package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleDetail;
import com.example.administrator.travel.bean.CircleFollow;
import com.example.administrator.travel.bean.CommonClickLikeBean;
import com.example.administrator.travel.event.CircleDetailEvent;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.CircleDetailAdapter;
import com.example.administrator.travel.ui.circle.createpost.CreatePostActivity;
import com.example.administrator.travel.ui.circle.post.PostActivity;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/7/8 0008.
 * 圈子
 */
public class CircleDetailActivity extends LoadingBarBaseActivity<CircleDetailEvent> implements View.OnClickListener {
    public static final int TYPE_LIKE_POST = 0;//点赞
    public static final int TYPE_LOAD_MORE = 1;//加载更多
    public static final int TYPE_REFRESH_POST = 2;//刷新
    public static final int TYPE_FIRST_REQ = 3;//普通加载
    public static final int TYPE_FOLLOW_CIRCLE = 4;//关注圈子
    private static final String TYPE_FOLLOW="1";//关注
    private static final String TYPE_CANCEL_FOLLOW="2";//取消关注
    private TextView mTvCreatePost;//创建帖子按钮
    private String cId;
    private boolean isFirst = true;//只有第一次才设置圈子名字和图片
    private List<CircleDetail.DataBean.BodyBean> postList = new ArrayList<>();
    private CircleDetailAdapter circleDetailAdapter;
    private ToShowAllListView mLvPost;
    private ImageView mIvPostBg;
    private ImageView mIvPostIcon;
    private TextView mTvPostNumber;
    private TextView mTvFollow;
    private TextView mTvFollowNumber;
    private TextView mTvDes;
    private TextView mTvCircleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_circle_detail;
    }

    @Override
    protected void initEvent() {
        XScrollView mSsvScroll = getxScrollView();
        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_circle_detail_content, null);
        if (mSsvScroll != null) {
            initXScrollView(false,true);
            //帖子列表
            mLvPost = (ToShowAllListView) inflate.findViewById(R.id.lv_post);
            //帖子背景
            mIvPostBg = (ImageView) inflate.findViewById(R.id.iv_post_bg);
            //帖子小头像
            mIvPostIcon = (ImageView) inflate.findViewById(R.id.iv_post_icon);
            //帖子数
            mTvPostNumber = (TextView) inflate.findViewById(R.id.tv_post_number);
            //关注数
            mTvFollow = (TextView) inflate.findViewById(R.id.tv_follow);
            //关注按钮
            mTvFollowNumber = (TextView) inflate.findViewById(R.id.tv_follow_number);
            //描述
            mTvDes = (TextView) inflate.findViewById(R.id.tv_des);
            //圈子名称
            mTvCircleName = (TextView) inflate.findViewById(R.id.tv_circle_name);
            mSsvScroll.setView(inflate);
            cId = getIntent().getStringExtra(IVariable.C_ID);
            mTvCreatePost = getmTvRightIcon();
            mTvCreatePost.setTypeface(TypefaceUtis.getTypeface(this));
            mTvCreatePost.setText(getResources().getString(R.string.activity_circle_create_post_font_icon));//设置创建帖子按钮
            mTvCreatePost.setOnClickListener(this);
            mTvFollow.setOnClickListener(this);
            mLvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CircleDetailActivity.this, PostActivity.class);
                    intent.putExtra(IVariable.FORUM_ID, postList.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> stringMap = MapUtils.Build().addKey(this).addCId(cId).addPageSize().addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CIRCLE_POST, stringMap, TYPE_FIRST_REQ,new CircleDetailEvent());
    }

    @Override
    protected Activity initViewData() {
        String cName = getIntent().getStringExtra(IVariable.C_NAME);
        changeTitle(cName);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "圈子详情";
    }

    @Override
    protected void onSuccess(CircleDetailEvent circleDetailEvent) {
        dealData(circleDetailEvent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_follow:
                followOrCancelFollow();
                break;
            case R.id.tv_right_icon:
                Intent intent = new Intent(CircleDetailActivity.this, CreatePostActivity.class);
                intent.putExtra(IVariable.C_ID,cId);
                startActivity(intent);
                break;
        }
    }

    /**
     * 判断是否需要取消还是关注
     */
    private void followOrCancelFollow() {
        if (mTvFollow==null)return;
        MapUtils.Builder builder = MapUtils.Build().addKey(this).add(IVariable.CIRCLE_ID, cId).add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId());
        //已关注
        if (mTvFollow.getText().toString().trim().equals(getString(R.string.activity_circle_detail_followed))){
            builder.add(IVariable.TYPE,TYPE_CANCEL_FOLLOW);
        }else {
            builder.add(IVariable.TYPE, TYPE_FOLLOW);
        }
        Map<String, String> followMap = builder.end();
        XEventUtils.postUseCommonBackJson(IVariable.CIRCLE_FOLLOW, followMap, TYPE_FOLLOW_CIRCLE,new CircleDetailEvent());
    }




    /**
     * 更新点赞数
     * @param result
     */
    private void dealLikeData(String result) {
        CommonClickLikeBean circleLike = GsonUtils.getObject(result, CommonClickLikeBean.class);
        String count_like = circleLike.getData().getCount_like();
        if (GlobalValue.CIRCLE_FOLLOW_LIKE_POSITION<postList.size()) {
            CircleDetail.DataBean.BodyBean bodyBean = postList.get(GlobalValue.CIRCLE_FOLLOW_LIKE_POSITION);
            bodyBean.setCount_like(count_like);
            bodyBean.setIs_like("1");//已赞
            circleDetailAdapter.notifyData(postList);
        }
    }

    /**
     * 处理数据
     * @param event
     */
    private void dealData(HttpEvent event) {
        if (event.getResult() == null) {
            return;
        }
        switch (event.getType()){
            case TYPE_LIKE_POST:
                dealLikeData(event.getResult());
                break;
            case TYPE_FIRST_REQ:
                dealCirclePostData(event);
                break;
            case TYPE_FOLLOW_CIRCLE:
                dealFollowData(event.getResult());
                break;
        }


    }

    /**
     * 处理关注信息
     * @param result
     */
    private void dealFollowData(String result) {
        CircleFollow circleFollow = GsonUtils.getObject(result, CircleFollow.class);
        String follow_count = circleFollow.getData().getFollow_count();
        mTvFollowNumber.setText(follow_count);
        if (mTvFollow.getText().toString().trim().equals(getString(R.string.activity_circle_detail_followed))){
            mTvFollow.setText(getString(R.string.activity_circle_detail_follow));
        }else {
            mTvFollow.setText(getString(R.string.activity_circle_detail_followed));
        }
    }

    /**
     * 处理帖子数据
     * @param event
     */
    private void dealCirclePostData(HttpEvent event) {
        CircleDetail circle = GsonUtils.getObject(event.getResult(), CircleDetail.class);
        CircleDetail.DataBean.HeadBean head = circle.getData().getHead();
        if (isFirst) {
            isFirst = false;
            ImageOptions imageOptions=new ImageOptions.Builder().setSize(DensityUtil.getScreenWidth(),DensityUtil.dip2px(202)).setCrop(true).build();
            x.image().bind(mIvPostBg, head.getTitle_img(),imageOptions);
            x.image().bind(mIvPostIcon, head.getCircle_ico());
            mTvCircleName.setText(head.getCname());
            mTvDes.setText("简介：" + head.getTitle());
            mTvFollow.setText(head.getIs_follow().equals("1")?getString(R.string.activity_circle_detail_followed):getString(R.string.activity_circle_detail_follow));
        }
        mTvFollowNumber.setText(head.getCount_follow());
        mTvPostNumber.setText(head.getCount_forum());
        List<CircleDetail.DataBean.BodyBean> body = circle.getData().getBody();
        if (!postList.contains(body)) {
            postList.addAll(body);
            if (circleDetailAdapter == null) {
                circleDetailAdapter = new CircleDetailAdapter(this, postList);
                mLvPost.setAdapter(circleDetailAdapter);
            } else {
                circleDetailAdapter.notifyData(postList);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }
}
