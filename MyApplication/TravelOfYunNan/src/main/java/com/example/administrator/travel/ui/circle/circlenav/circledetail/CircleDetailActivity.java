package com.example.administrator.travel.ui.circle.circlenav.circledetail;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseChangeColorRecycleActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.createpost.CreatePostActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/8 0008.
 * 圈子
 */
public class CircleDetailActivity extends BaseChangeColorRecycleActivity<CircleDetailEvent,CircleDetailBean,CircleDetailBean.DataBean,CircleDetailBean.DataBean.BodyBean> implements View.OnClickListener {

    private static final String TYPE_FOLLOW="1";//关注
    private static final String TYPE_CANCEL_FOLLOW="2";//取消关注
    private String cId;

    @BindView(R.id.iv_post_bg) SimpleDraweeView mIvPostBg;
    @BindView(R.id.iv_post_icon) SimpleDraweeView mIvPostIcon;
    @BindView(R.id.tv_post_number) TextView mTvPostNumber;
    @BindView(R.id.tv_follow) TextView mTvFollow;
    @BindView(R.id.tv_follow_number) TextView mTvFollowNumber;
    @BindView(R.id.tv_des) TextView mTvDes;
    @BindView(R.id.tv_circle_name) TextView mTvCircleName;
    private boolean isFirst;

    @Override
    protected void initHeader() {
        cId = getIntent().getStringExtra(IVariable.C_ID);
        mVsHeader.setLayoutResource(R.layout.activity_circle_detail_header);
        mVsHeader.inflate();
    }
    public static void start(Context context,String cid){
        Intent intent=new Intent(context,CircleDetailActivity.class);
        intent.putExtra(IVariable.C_ID,cid);
        context.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return "圈子详情";
    }

    @Override
    protected String initUrl() {
        return IVariable.GET_CIRCLE_POST;
    }




    @Override
    protected void onSuccess(CircleDetailEvent circleDetailEvent) {

        switch (circleDetailEvent.getType()) {
            case TYPE_LOAD:
            case TYPE_REFRESH:
                super.onSuccess(circleDetailEvent);
                isFirst = true;
                if (isFirst) {
                    isFirst=false;
                    dealHeader(circleDetailEvent);
                }
                break;
            case TYPE_LIKE_POST:
                dealLikeData(circleDetailEvent);
                break;
            case TYPE_FOLLOW_CIRCLE:
                dealFollowData(circleDetailEvent.getResult());
                break;

        }
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        CreatePostActivity.start(this,cId,12,CreatePostActivity.REPLY_POST,"","","");
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<CircleDetailBean.DataBean.BodyBean> mDatas) {
        return new CircleDetailAdapter(mDatas,this);
    }

    @Override
    protected void initChildListener() {
        mTvFollow.setOnClickListener(this);

    }





    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addCId(cId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_follow:
                followOrCancelFollow();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.circle_detail_menu,menu);
        return true;
    }

    /**
     * 更新点赞数
     * @param result
     */
    private void dealLikeData(CircleDetailEvent result) {
        CommonClickLikeBean circleLike = GsonUtils.getObject(result.getResult(), CommonClickLikeBean.class);
        String count_like = circleLike.getData().getCount_like();
        CircleDetailBean.DataBean.BodyBean bodyBean = mDatas.get(result.getPosition());
        bodyBean.setCount_like(count_like);
        bodyBean.setIs_like("1");//已赞
        mAdapter.notifyDataSetChanged();

    }



    /**
     * 处理关注信息
     * @param result
     */
    private void dealFollowData(String result) {
        CircleFollowBean circleFollow = GsonUtils.getObject(result, CircleFollowBean.class);
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
     */
    private void dealHeader(HttpEvent event) {
        CircleDetailBean circle = GsonUtils.getObject(event.getResult(), CircleDetailBean.class);
        CircleDetailBean.DataBean.HeadBean head = circle.getData().getHead();
        FrescoUtils.displayNormal(mIvPostBg,head.getTitle_img());
        FrescoUtils.displayNormal(mIvPostIcon,head.getCircle_ico());
        mTvCircleName.setText(head.getCname());
        mTvDes.setText("简介：" + head.getTitle());
        mTvFollow.setText(head.getIs_follow().equals("1")?getString(R.string.activity_circle_detail_followed):getString(R.string.activity_circle_detail_follow));
        mTvFollowNumber.setText(head.getCount_follow());
        mTvPostNumber.setText(head.getCount_forum());
        mTvTitle.setText(head.getCname());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
