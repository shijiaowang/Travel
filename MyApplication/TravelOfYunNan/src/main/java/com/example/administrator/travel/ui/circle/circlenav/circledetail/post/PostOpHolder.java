package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWitheMeDecoration;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/11 0011.
 * 帖子头部
 */
 class PostOpHolder extends BaseRecycleViewHolder {

    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rv_post_image)
    RecyclerView mRvPostImage;
    @BindView(R.id.fitv_like)
    FontsIconTextView mFitvLike;
    @BindView(R.id.tv_like_user)
    TextView mTvLikeUser;
    @BindView(R.id.tv_discuss_count)
    TextView mTvDiscussCount;
    private boolean isFirst=true;
    public PostOpHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, Object data, Context mContext) {
        PostDetailBean.DataBean.ForumBean  forum;
        if (data instanceof PostDetailBean.DataBean.ForumBean) {
            forum = (PostDetailBean.DataBean.ForumBean) data;
        } else {
            return;
        }
        if (isFirst) {
            isFirst = false;
            mTvNickName.setText(forum.getNick_name());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm", forum.getTime()));
            mTvContent.setText(forum.getContent());
            FrescoUtils.displayIcon(mIvUserIcon,forum.getUser_img());
            if (!StringUtils.isEmpty(forum.getForum_img())) {
                String[] split = forum.getForum_img().split(",");
                List<String> list = Arrays.asList(split);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
                mRvPostImage.setAdapter(new PostImageAdapter(list));
                mRvPostImage.setLayoutManager(linearLayoutManager);
                mRvPostImage.addItemDecoration(new MyWitheMeDecoration(6));

            }
        }
        List<PostDetailBean.DataBean.ForumBean.LikeBean> like = forum.getLike();

        boolean isLike = false;
        StringBuffer likeName = new StringBuffer();
        if (like != null && like.size() != 0) {
            for (PostDetailBean.DataBean.ForumBean.LikeBean bean : like) {
                if (!StringUtils.isEmpty(bean.getNick_name())) {
                    likeName.append(bean.getNick_name() + "、");
                }
                if (bean.getId().equals(GlobalUtils.getUserInfo().getId())) {
                    isLike = true;
                }
            }
            if (!StringUtils.isEmpty(likeName.toString())) {
                String likeUser = likeName.toString().substring(0, likeName.length() - 1);//去掉最后的点
                mTvLikeUser.setText(likeUser);
            }
        }
        mTvDiscussCount.setText("评论("+forum.getReplay_count()+")");
        mFitvLike.setTextColor(isLike ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));

    }
}
