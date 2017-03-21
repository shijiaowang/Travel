package com.yunspeak.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.PostDetailBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.TopDecoration;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.HowLongWithCurrentUtils;
import com.yunspeak.travel.utils.StringUtils;

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
    private boolean isFirst = true;

    public PostOpHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, Object data, final Context mContext) {
        final PostDetailBean.DataBean.ForumBean forum;
        if (data instanceof PostDetailBean.DataBean.ForumBean) {
            forum = (PostDetailBean.DataBean.ForumBean) data;
        } else {
            return;
        }
        mIvUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserCenterActivity.start(mContext, mIvUserIcon, forum.getUser_id());
            }
        });
        mTvTime.setText(HowLongWithCurrentUtils.getDesStringFromTime(forum.getTime(),"yyyy-MM-dd HH:mm"));
        if (isFirst) {
            isFirst = false;
            mTvNickName.setText(forum.getNick_name());
            AiteUtils.parseTextMessage(mTvContent,forum.getInform(),forum.getContent(),true);
            FrescoUtils.displayIcon(mIvUserIcon, forum.getUser_img());
            if (!StringUtils.isEmpty(forum.getForum_img())) {
                String[] split = forum.getForum_img().split(",");
                List<String> list = Arrays.asList(split);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                mRvPostImage.setVisibility(View.VISIBLE);
                mRvPostImage.setAdapter(new PostImageAdapter(list));
                mRvPostImage.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setAutoMeasureEnabled(true);
                mRvPostImage.setHasFixedSize(true);
                mRvPostImage.addItemDecoration(new TopDecoration(6));
            } else {
                mRvPostImage.setVisibility(View.GONE);
            }
        }
        List<PostDetailBean.DataBean.ForumBean.LikeBean> like = forum.getLike();

        StringBuffer likeName = new StringBuffer();
        if (like != null && like.size() != 0) {
            int limit = 5;//超过五个不在遍历
            int currentPosition = 0;
            for (PostDetailBean.DataBean.ForumBean.LikeBean bean : like) {
                currentPosition++;
                if (!StringUtils.isEmpty(bean.getNick_name())) {
                    likeName.append(bean.getNick_name()).append("、");
                }
                if (currentPosition >= limit) {
                    break;
                }

            }

            if (!StringUtils.isEmpty(likeName.toString())) {
                String likeUser = likeName.toString().substring(0, likeName.length() - 1);//去掉最后的点
                String other=like.size()>limit?"等"+(like.size()-limit)+"人觉得很赞!":"";
                mTvLikeUser.setText(likeUser+other);
            }
        }
        mTvDiscussCount.setText("评论(" + forum.getReplay_count() + ")");
        boolean isLike = forum.getIs_like().equals("1");
        mFitvLike.setTextColor(isLike ? mContext.getResources().getColor(R.color.otherFf7f6c) : mContext.getResources().getColor(R.color.color969696));
        mFitvLike.setText(isLike ? mContext.getString(R.string.activity_circle_love_full) : mContext.getString(R.string.activity_circle_love_empty));
    }
}
